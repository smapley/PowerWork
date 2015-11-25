package com.smapley.powerwork.application;

import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.smapley.powerwork.BuildConfig;
import com.smapley.powerwork.R;
import com.smapley.powerwork.exception.BaseExceptionHandler;
import com.smapley.powerwork.exception.LocalFileHandler;
import com.smapley.powerwork.utils.JFileKit;
import com.smapley.powerwork.utils.MyData;

import org.xutils.DbManager;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

/**
 * Created by smapley on 15/10/22.
 */
public class LocalApplication extends BaseApplication {

    private static LocalApplication instance;


    public DbManager dbUtils;
    private DbManager.DaoConfig daoConfig ;

    public ImageOptions CirtlesImage;
    public ImageOptions FilletImage;

    //当前屏幕的高宽
    public int screenW = 0;
    public int screenH = 0;

    //单例模式中获取唯一的MyApplication实例
    public static LocalApplication getInstance() {
        if (instance == null) {
            instance = new LocalApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建log目录
        File logFolder = new File(JFileKit.getDiskCacheDir(this) + MyData.File_Log);
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }
        //创建audio目录
        File audioFolder = new File(JFileKit.getDiskCacheDir(this) + MyData.File_Audio);
        if (!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        //初始化xUtils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        //初始化数据库
        daoConfig = new DbManager.DaoConfig()
                .setDbName("PowerWork")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });
        dbUtils = x.getDb(daoConfig);

        //初始化圆形图片
        CirtlesImage = new ImageOptions.Builder()
                //   .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                .setRadius(DensityUtil.dip2px(100))
                        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                        // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.logo)
                .setFailureDrawableId(R.mipmap.logo)
                .build();
        //初始化圆角图片
        FilletImage=new ImageOptions.Builder()
                .setRadius(DensityUtil.dip2px(10))
                        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                        // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.logo)
                .setFailureDrawableId(R.mipmap.logo)
                .build();


        instance = this;

        //得到屏幕的宽度和高度
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenW = displayMetrics.widthPixels;
        screenH = displayMetrics.heightPixels;
    }

    @Override
    public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
        return new LocalFileHandler(applicationContext);
    }
}
