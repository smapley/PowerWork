package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Add;
import com.smapley.powerwork.adapter.ProItem3Adapter;
import com.smapley.powerwork.db.entity.FileEntity;
import com.smapley.powerwork.db.entity.FolderEntity;
import com.smapley.powerwork.db.mode.FolderMode;
import com.smapley.powerwork.db.service.FolderService;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item3)
public class Pro_Item3 extends BaseFragment {


    private static final int SAVEFOLDER = 1;
    private static final int SAVEFILE = 2;
    private static final int GETFOLDER = 3;
    private static final int TONEXTFOLDER = 5;
    private static final int GOBACKFOLDER = 6;
    private static final int GETFILE = 7;
    @ViewInject(R.id.pro_item3_rv_list)
    private RecyclerView pro_item3_rv_list;


    private List<BaseMode> list_data;

    private ProItem3Adapter adapter;

    private PopupWindow pop_add;
    private PopupWindow pop_file;

    private int pro_id;
    private int baseFol_id = -1;
    private int fol_id = -1;


    @Override
    protected void initParams(View view) {
        initData();
        initView();
        getDataForDb();
        getDataForWeb();
    }



    private void initData() {
        pro_id = getArguments().getInt("pro_id");
        FolderEntity folderEntity = null;
        try {
            folderEntity = dbUtils.selector(FolderEntity.class)
                    .where("pro_id", "=", pro_id + "").findFirst();
            if (folderEntity != null) {
                fol_id = folderEntity.getFol_id();
                baseFol_id = fol_id;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        pro_item3_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_data = new ArrayList<>();
        adapter = new ProItem3Adapter(getActivity(), this, list_data);
        pro_item3_rv_list.setAdapter(adapter);
    }

    @Event({R.id.pro_item3_tv_add})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.pro_item3_tv_add:
                showAddPopupWindow(view);
                break;

        }
    }

    public void getDataForDb() {
        getFolderForDb();
        getFileForDb();
    }

    private void getFileForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (fol_id != -1) {
                        List<FileEntity> listFolder = dbUtils.selector(FileEntity.class)
                                .where("fol_id", "=", fol_id).findAll();
                        if (listFolder != null) {
                            mhandler.obtainMessage(GETFILE, listFolder).sendToTarget();
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getFolderForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (fol_id != -1) {
                        List<FolderEntity> listFolder = dbUtils.selector(FolderEntity.class)
                                .where("fol_id2", "=", fol_id).findAll();
                        if (listFolder != null) {
                            //如果不是最上层folder则添加返回
                            if (fol_id != baseFol_id) {
                                FolderEntity backFolderEntity = new FolderEntity();
                                backFolderEntity.setName("......");
                                backFolderEntity.setIsBack(true);
                                backFolderEntity.setFol_id2(fol_id);
                                listFolder.add(0, backFolderEntity);
                            }
                            mhandler.obtainMessage(GETFOLDER, listFolder).sendToTarget();
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void goBackFolder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (fol_id != -1) {
                        FolderEntity folderEntity = dbUtils.findById(FolderEntity.class, fol_id);
                        if (folderEntity != null)
                            if (folderEntity.getFol_id2() != 0) {
                                fol_id = folderEntity.getFol_id2();
                                List<FolderEntity> listFolder = dbUtils.selector(FolderEntity.class)
                                        .where("fol_id2", "=", fol_id).findAll();
                                if (listFolder != null && !listFolder.isEmpty()) {
                                    //如果不是最上层folder则添加返回
                                    if (fol_id != baseFol_id) {
                                        FolderEntity backFolderEntity = new FolderEntity();
                                        backFolderEntity.setName("......");
                                        backFolderEntity.setIsBack(true);
                                        backFolderEntity.setFol_id2(fol_id);
                                        listFolder.add(0, backFolderEntity);
                                    }
                                    mhandler.obtainMessage(GETFOLDER, listFolder).sendToTarget();
                                }
                            }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getDataForWeb() {
        getFolderForWeb();
        getFileForWeb();
    }

    private void getFolderForWeb() {
        BaseParams params = new BaseParams(MyData.URL_FolderList, userEntity);
        params.addBodyParameter("pro_id", pro_id + "");
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.flag.equals(MyData.SUCC)) {
                            FolderMode folderMode = JSON.parseObject(result.data, new TypeReference<FolderMode>() {
                            });
                            if (folderMode != null ) {
                                FolderService.save(folderMode);
                                mhandler.obtainMessage(SAVEFOLDER).sendToTarget();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getFileForWeb() {
        BaseParams params = new BaseParams(MyData.URL_FileList, userEntity);
        params.addBodyParameter("pro_id", pro_id + "");
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.flag.equals(MyData.SUCC)) {
                            List<FileEntity> listFile = JSON.parseObject(result.data, new TypeReference<List<FileEntity>>() {
                            });
                            if (listFile != null && !listFile.isEmpty()) {
                                for (FileEntity fileEntity : listFile) {
                                    try {
                                        dbUtils.saveOrUpdate(fileEntity);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                                mhandler.obtainMessage(SAVEFILE).sendToTarget();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SAVEFOLDER:
                    getFolderForDb();
                    break;
                case SAVEFILE:
                    getFileForDb();
                    break;
                case GETFOLDER:
                    adapter.removeFolder();
                    adapter.addFolder((List<FolderEntity>) msg.obj);
                    break;
                case GETFILE:
                    adapter.removeFile();
                    adapter.addFile((List<FileEntity>) msg.obj);
                    break;
                case TONEXTFOLDER:
                    fol_id = (int) msg.obj;
                    getFolderForDb();
                    getFileForDb();
                    break;
                case GOBACKFOLDER:
                    fol_id = (int) msg.obj;
                    if (fol_id != -1)
                        try {
                            FolderEntity folderEntity = dbUtils.findById(FolderEntity.class, fol_id);
                            fol_id=folderEntity.getFol_id2();
                            getFolderForDb();
                            getFileForDb();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    break;
            }
        }
    };

    /**
     * 显示新建文件PopupWindow
     */
    private void showAddPopupWindow(View view) {
        //初始化
        initAddPopupWindow();
        pop_add.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //设置背景变暗
        pop_add.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getActivity().getWindow().setAttributes(layoutParams);
    }


    /**
     * 初始化新建文件PopupWindow
     */
    private void initAddPopupWindow() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_pro_item3_add, null);
        pop_add = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        pop_add.setFocusable(true);
        pop_add.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindow出现和消失动画
        pop_add.setAnimationStyle(R.style.pro_item5_add);
        //监听popupwindow消失动作
        pop_add.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hitAddPopupWindow();
            }
        });

        //监听按钮点击
        TextView newfile = (TextView) popView.findViewById(R.id.pro_item3_tv_nowfile);
        TextView newfiles = (TextView) popView.findViewById(R.id.pro_item3_tv_nowfiles);
        newfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAddPopupWindow();
                showFilePopupWindow(view);
            }
        });

        newfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAddPopupWindow();
                Intent intent=new Intent(getActivity(), Add.class);
                intent.putExtra("src",1);
                getActivity().startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * 隐藏新建文件PopupWindow
     */
    private void hitAddPopupWindow() {
        if (pop_add.isShowing())
            pop_add.dismiss();
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 1f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    /**
     * 显示选择文件PopupWindow
     */
    private void showFilePopupWindow(View view) {
        //初始化
        initFilePopupWindow();
        pop_file.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        pop_file.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    /**
     * 初始化选择文件PopupWindow
     */
    private void initFilePopupWindow() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_pro_item3_file, null);
        pop_file = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        pop_file.setFocusable(true);
        pop_file.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindow出现和消失动画
        pop_file.setAnimationStyle(R.style.pop_voice);
        //监听popupwindow消失动作
        pop_file.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hitFilePopupWindow();
            }
        });

        //监听按钮点击
        TextView item1 = (TextView) popView.findViewById(R.id.pro_item3_tv_item1);
        TextView item2 = (TextView) popView.findViewById(R.id.pro_item3_tv_item2);
        TextView item3 = (TextView) popView.findViewById(R.id.pro_item3_tv_item3);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitFilePopupWindow();
                selectPic();
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitFilePopupWindow();
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitFilePopupWindow();
            }
        });
    }

    /**
     * 隐藏选择文件PopupWindow
     */
    private void hitFilePopupWindow() {
        if (pop_file.isShowing())
            pop_file.dismiss();
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 1f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    /**
     * 从相册选择头像
     */
    private void selectPic() {
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        boolean showCamera = true;
        int maxNum = 8;
        Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        getActivity().startActivityForResult(intent, 2);
    }

    /**
     * 新建文件夹
     */
    public void addFolder(String name) {
        if (fol_id != -1) {
            BaseParams params = new BaseParams(MyData.URL_AddFolder, userEntity);
            params.addBodyParameter("fol_id", fol_id + "");
            params.addBodyParameter("name", name);
            x.http().post(params, new HttpCallBack(getActivity(), R.string.addfolder_ing) {
                @Override
                public void onResult(final String result, SweetAlertDialog dialog) {
                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                            .showText(R.string.addfolder_succ)
                            .commit().dismiss(2000);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FolderEntity folderEntity = JSON.parseObject(result, new TypeReference<FolderEntity>() {
                            });
                            if (folderEntity != null) {
                                try {
                                    dbUtils.save(folderEntity);
                                    mhandler.obtainMessage(SAVEFOLDER).sendToTarget();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            });

        }

    }

    /**
     * 新建文件
     */
    public void addFile(int type, List<String> list) {
        BaseParams params = new BaseParams(MyData.URL_AddFile, userEntity);
        params.addBodyParameter("pro_id",pro_id+"");
        params.addBodyParameter("fol_id", fol_id + "");
        params.addBodyParameter("size", list.size() + "");
        params.setMultipart(true);
        for (int i = 0; i < list.size(); i++) {
            params.addBodyParameter("type" + i, type+"");
            params.addBodyParameter("file" + i, new File(list.get(i)));
        }
        x.http().post(params, new HttpCallBack(getActivity(), R.string.addfolder_ing) {
            @Override
            public void onResult(final String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        .showText(R.string.addfolder_succ)
                        .commit()
                        .dismiss(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<FileEntity> listFile=JSON.parseObject(result,new TypeReference<List<FileEntity>>(){});
                        for(FileEntity FileEntity:listFile){
                            try {
                                dbUtils.save(FileEntity);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        mhandler.obtainMessage(SAVEFILE).sendToTarget();
                    }
                }).start();
            }
        });
    }


}
