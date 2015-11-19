package com.smapley.powerwork.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.cache.LruDiskCache;
import com.lidroid.xutils.util.LogUtils;
import com.smapley.powerwork.utils.BitmapUtil;
import com.smapley.powerwork.utils.DullPolish;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by smapley on 15/10/22.
 * <p/>
 * 异步加载图片的任务
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    //异步加载图片工具
    private AsyncImageLoader imageLoader;
    //显示图片的控件所在的视图
    private ImageView imageView;
    //图片url地址
    protected String imageUrl;

    //生成图片的宽度
    private int reqWidth;
    //生成图片的高度
    private int reqHeight;
    //是否进行模糊处理
    private boolean isDullPolish;

    public BitmapWorkerTask(AsyncImageLoader imageLoader, ImageView imageView, int reqWidth, int reqHeight, boolean isDullPolish) {
        this.imageLoader = imageLoader;
        this.imageView = imageView;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        this.isDullPolish = isDullPolish;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        imageUrl = strings[0];
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        //硬盘缓存读取对象
        LruDiskCache.Snapshot snapshot = null;
        try {
            //生成图片URL对应的key
            final String key = imageLoader.hashKeyForDisk(imageUrl);
            //查找key对应的缓存
            snapshot = imageLoader.diskCache.get(key);
            if (snapshot == null) {
                //如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
                LruDiskCache.Editor editor = imageLoader.diskCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    //从网络获取bitmap写入制定输入流
                    if (downloadUrlToStream(imageUrl, outputStream)) {
                        //提交生效
                        editor.commit();
                    } else {
                        //放弃此次写入
                        editor.abort();
                    }
                }
                //缓存被写入后，在此查找key对应的缓存
                snapshot = imageLoader.diskCache.get(key);
                LogUtils.i("load_network" + imageUrl);
            } else
                LogUtils.i("load_disk" + imageUrl);

            if (snapshot != null) {
                //读取缓存文件
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            //将缓存数据解析成bitmap对象
            Bitmap bitmap = null;
            if (fileDescriptor != null) {
                bitmap = BitmapUtil.decodeSampledBitmap(fileDescriptor, reqWidth, reqHeight);
            }
            if (bitmap != null) {
                //将bitmap对象添加到内存缓存当中
                imageLoader.addBitmapToMemoryCache(strings[0], bitmap);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor != null && fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        //根据tag找到相应的imageview控件
        if (imageView != null) {
            //加载成功显示图片
            if (result != null) {
                imageView.setImageBitmap(isDullPolish ? DullPolish.doPolish(imageLoader.getContext(), result) : result);
                LogUtils.i("load_success" + imageUrl);

            } else {
                //加载失败显示图片
                if (imageLoader.loadfailBitmap != null) {
                    imageView.setImageBitmap(isDullPolish ? DullPolish.doPolish(imageLoader.getContext(), imageLoader.loadfailBitmap) : imageLoader.loadfailBitmap);
                }
                LogUtils.i("load_fail" + imageUrl);
            }
        }

        //从任务集合中移除当前任务
        imageLoader.taskCollection.remove(this);
    }


    /**
     * 建立http请求，获取bitmap对象写入流
     */
    private boolean downloadUrlToStream(String imageUrl, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
