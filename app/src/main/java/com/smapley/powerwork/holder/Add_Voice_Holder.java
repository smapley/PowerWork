package com.smapley.powerwork.holder;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;

import java.text.SimpleDateFormat;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Voice_Holder extends BaseHolder {

    private TextView add_tv_voice_time;
    private ImageView add_iv_voice_play;
    private SeekBar add_sb_voice_bar;

    private MediaPlayer mediaPlayer;
    private boolean isPlay = false;
    private long playlength;
    private SimpleDateFormat simpleDateFormat;
    private final int PLAY = 1;

    public Add_Voice_Holder(View view) {
        super(view);
        add_tv_voice_time = (TextView) view.findViewById(R.id.add_tv_voice_time);
        add_iv_voice_play = (ImageView) view.findViewById(R.id.add_iv_voice_play);
        add_sb_voice_bar = (SeekBar) view.findViewById(R.id.add_sb_voice_bar);
    }


    public void setData(Context context, final NoteDetailsEntity mode) {
        simpleDateFormat = new SimpleDateFormat("mm:ss");
        playlength = mode.getLength();
        add_tv_voice_time.setText(simpleDateFormat.format(mode.getLength()));
        add_iv_voice_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(mode.getPath());
                        mediaPlayer.prepare();
                        add_sb_voice_bar.setMax(mediaPlayer.getDuration());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPlay = false;
                    add_iv_voice_play.setImageResource(R.mipmap.play_iv);
                } else {
                    mediaPlayer.start();
                    isPlay = true;
                    add_iv_voice_play.setImageResource(R.mipmap.pause_iv);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            add_iv_voice_play.setImageResource(R.mipmap.play_iv);
                            isPlay = false;
                            playlength = mode.getLength();
                            add_tv_voice_time.setText(simpleDateFormat.format(mode.getLength()));
                            add_sb_voice_bar.setProgress(0);
                        }
                    });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isPlay) {
                                mhandler.obtainMessage(PLAY).sendToTarget();
                                add_sb_voice_bar.setProgress(mediaPlayer.getCurrentPosition());
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
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PLAY:
                    if (isPlay) {
                        playlength -= 10;
                        if (playlength >= 57600000)
                            add_tv_voice_time.setText(simpleDateFormat.format(playlength));
                    }
                    break;
            }
        }
    };
}
