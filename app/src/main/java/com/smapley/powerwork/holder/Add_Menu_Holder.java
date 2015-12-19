package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.AddTask;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.utils.JFileKit;
import com.smapley.powerwork.utils.voice.ErrorCode;
import com.smapley.powerwork.utils.voice.MediaRecordFunc;

import java.text.SimpleDateFormat;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Menu_Holder extends BaseHolder {

    private ImageView add_im_menu_annex;
    private ImageView add_im_menu_pic;
    private ImageView add_im_menu_voice;
    private ImageView add_im_menu_write;

    private PopupWindow add_pop_voice;

    private boolean isVoice = false;
    private boolean isPlay = false;

    private final int CLOCK = 1;
    private final int PLAY = 2;

    private boolean isClock = false;

    private LinearLayout pop_add_circle_big;
    private LinearLayout pop_add_circle_smo;
    private TextView pop_add_text;

    private LinearLayout pop_add_voice_bottom;
    private ImageView pop_add_voice_iv_play;
    private SeekBar pop_add_voice_sb_bar;
    private ImageView pop_add_voice_iv_close;
    private TextView pop_add_voice_tv_time;
    private TextView pop_add_voice_tv_cancel;
    private TextView pop_add_voice_tv_ok;

    private long voicelength = 5760000;
    private long playlength = 0;
    private SimpleDateFormat simpleDateFormat;

    private MediaPlayer mediaPlayer;


    /**
     * 初始化录音PopupWindow
     */
    private void initPopWindow(final Context context, final AddTaskAdapter adapter) {
        View popView = LayoutInflater.from(context).inflate(R.layout.popup_add_voice, null);
        add_pop_voice = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        add_pop_voice.setOutsideTouchable(false);
        add_pop_voice.setFocusable(true);
        //设置popwindow出现和消失动画
        add_pop_voice.setAnimationStyle(R.style.pop_voice);

        final ImageView pop_add_voice = (ImageView) popView.findViewById(R.id.pop_add_voice);
        pop_add_circle_big = (LinearLayout) popView.findViewById(R.id.pop_add_circle_big);
        pop_add_circle_smo = (LinearLayout) popView.findViewById(R.id.pop_add_circle_smo);
        pop_add_text = (TextView) popView.findViewById(R.id.pop_add_text);
        pop_add_voice_bottom = (LinearLayout) popView.findViewById(R.id.pop_add_voice_bottom);
        pop_add_voice_iv_play = (ImageView) popView.findViewById(R.id.pop_add_voice_iv_play);
        pop_add_voice_sb_bar = (SeekBar) popView.findViewById(R.id.pop_add_voice_sb_bar);
        pop_add_voice_iv_close = (ImageView) popView.findViewById(R.id.pop_add_voice_iv_close);
        pop_add_voice_tv_time = (TextView) popView.findViewById(R.id.pop_add_voice_tv_time);
        pop_add_voice_tv_cancel = (TextView) popView.findViewById(R.id.pop_add_voice_tv_cancel);
        pop_add_voice_tv_ok = (TextView) popView.findViewById(R.id.pop_add_voice_tv_ok);
        popView.findViewById(R.id.pop_add_voice_iv_play);
        pop_add_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化数据
                isVoice = !isVoice;
                voicelength = isVoice ? 57600000 : voicelength;
                simpleDateFormat = new SimpleDateFormat("mm:ss");

                MediaRecordFunc mRecord = MediaRecordFunc.getInstance(context);
                if (isVoice) {
                    mediaPlayer = null;
                    //开始录音，如果有错误则输出错误
                    pop_add_voice_bottom.setVisibility(View.GONE);
                    int result = mRecord.startRecordAndFile();
                    if (result == ErrorCode.SUCCESS) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (isVoice) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    mhandler.obtainMessage(CLOCK).sendToTarget();
                                }
                            }
                        }).start();
                    } else {
                        isVoice = !isVoice;
                        Toast.makeText(context, ErrorCode.getErrorInfo(context, result), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //停止录音
                    mRecord.stopRecordAndFile();
                    pop_add_voice_bottom.setVisibility(View.VISIBLE);
                    voicelength += 510;
                    playlength = voicelength;
                    pop_add_voice_tv_time.setText(simpleDateFormat.format(playlength));
                }
                //更新ui
                pop_add_text.setText(isVoice ? simpleDateFormat.format(voicelength) : context.getString(R.string.pop_add_voice_action));
                pop_add_voice.setImageResource(isVoice ? R.mipmap.pop_add_voice_2 : R.mipmap.pop_add_voice_1);
                pop_add_circle_smo.setBackgroundResource(isVoice ? R.drawable.circles_gray : R.drawable.circles_white);
                pop_add_circle_big.setBackgroundResource(isVoice ? R.drawable.circles_blue_gray : R.drawable.circles_white);

            }
        });

        /**
         * 播放按钮
         */
        pop_add_voice_iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(JFileKit.AUDIO_AMR_FILEPATH);
                        mediaPlayer.prepare();
                        pop_add_voice_sb_bar.setMax(mediaPlayer.getDuration());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPlay = false;
                    pop_add_voice_iv_play.setImageResource(R.mipmap.play_iv);
                } else {
                    mediaPlayer.start();
                    isPlay = true;
                    pop_add_voice_iv_play.setImageResource(R.mipmap.pause_iv);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            pop_add_voice_iv_play.setImageResource(R.mipmap.play_iv);
                            isPlay = false;
                            playlength = voicelength;
                            pop_add_voice_tv_time.setText(simpleDateFormat.format(playlength));
                            pop_add_voice_sb_bar.setProgress(0);
                        }
                    });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isPlay) {
                                mhandler.obtainMessage(PLAY).sendToTarget();
                                pop_add_voice_sb_bar.setProgress(mediaPlayer.getCurrentPosition());
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }).start();
                }


            }
        });

        /**
         * 关闭录音popupWindow
         */
        pop_add_voice_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitPopVoice(context);
            }
        });

        /**
         * 添加按钮
         */
        pop_add_voice_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDetailsEntity add_item_mode=new NoteDetailsEntity(4);
                add_item_mode.setPath(JFileKit.AUDIO_AMR_FILEPATH);
                add_item_mode.setLength(voicelength);
                adapter.addItem(add_item_mode);
                adapter.addItem(new NoteDetailsEntity(5));
                hitPopVoice(context);
            }
        });
    }

    /**
     * 显示录音PopupWindow
     */
    private void showPopVoice(Context context, View view, AddTaskAdapter adapter) {
        //初始化
        initPopWindow(context, adapter);

        add_pop_voice.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        add_pop_voice.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = ((AddTask) context).getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        ((AddTask) context).getWindow().setAttributes(layoutParams);
    }

    /**
     * 隐藏录音PopupWindow
     */
    private void hitPopVoice(Context context) {
        add_pop_voice.dismiss();
        WindowManager.LayoutParams layoutParams = ((AddTask) context).getWindow().getAttributes();
        layoutParams.alpha = 1f;
        ((AddTask) context).getWindow().setAttributes(layoutParams);
    }

    public Add_Menu_Holder(View view) {
        super(view);

        add_im_menu_annex = (ImageView) view.findViewById(R.id.add_im_menu_annex);
        add_im_menu_pic = (ImageView) view.findViewById(R.id.add_im_menu_pic);
        add_im_menu_voice = (ImageView) view.findViewById(R.id.add_im_menu_voice);
        add_im_menu_write = (ImageView) view.findViewById(R.id.add_im_menu_write);

    }

    public void setData(final Context context, final AddTaskAdapter adapter, final int position) {


        add_im_menu_annex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add_im_menu_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPic(context);
            }
        });
        add_im_menu_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopVoice(context, view, adapter);
            }
        });
        add_im_menu_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    /**
     * 从相册选择头像
     */
    private void selectPic(Context context) {
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        boolean showCamera = true;
        int maxNum = 8;
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
//                if (mSelectPath != null && mSelectPath.size() > 0) {
//                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//                }
        ((AddTask) context).startActivityForResult(intent, 3);
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLOCK:
                    if (isVoice) {
                        voicelength += 1000;
                        pop_add_circle_big.setBackgroundResource(isClock ? R.drawable.circles_blue_gray : R.drawable.circles_blue_height);
                        isClock = !isClock;
                        pop_add_text.setText(simpleDateFormat.format(voicelength));
                    }
                    break;
                case PLAY:
                    if (isPlay) {
                        playlength -= 10;
                        if (playlength >= 57600000)
                            pop_add_voice_tv_time.setText(simpleDateFormat.format(playlength));
                    }
                    break;
            }
        }
    };
}
