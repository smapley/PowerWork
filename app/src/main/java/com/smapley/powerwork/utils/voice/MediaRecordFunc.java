package com.smapley.powerwork.utils.voice;

import android.content.Context;
import android.media.MediaRecorder;

import com.smapley.powerwork.utils.JFileKit;

import java.io.File;
import java.io.IOException;

/**
 * Created by smapley on 15/11/15.
 */
public class MediaRecordFunc {
    private boolean isRecord = false;

    private MediaRecorder mMediaRecorder;

    private static Context mcontext;

    private MediaRecordFunc() {
    }

    private static MediaRecordFunc mInstance;

    public synchronized static MediaRecordFunc getInstance(Context context) {
        mcontext = context;
        if (mInstance == null)
            mInstance = new MediaRecordFunc();
        return mInstance;
    }

    public int startRecordAndFile() {
        if (isRecord) {
            return ErrorCode.E_STATE_RECODING;
        } else {
            if (mMediaRecorder == null)
                createMediaRecord();

            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
                // 让录制状态为true
                isRecord = true;
                return ErrorCode.SUCCESS;
            } catch (IOException ex) {
                ex.printStackTrace();
                return ErrorCode.E_UNKOWN;
            }
        }


    }


    public void stopRecordAndFile() {
        close();
    }

    public long getRecordFileSize() {
        return JFileKit.getFileSize(JFileKit.AUDIO_AMR_FILEPATH);
    }


    private void createMediaRecord() {
         /* ①Initial：实例化MediaRecorder对象 */
        mMediaRecorder = new MediaRecorder();

        /* setAudioSource/setVedioSource*/
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风

        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
         */
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

         /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

         /* 设置输出文件的路径 */
        File file = new File(JFileKit.getAMRFilePath(mcontext));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder.setOutputFile(JFileKit.AUDIO_AMR_FILEPATH);
    }


    private void close() {
        if (mMediaRecorder != null) {
            System.out.println("stopRecord");
            isRecord = false;
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
}
