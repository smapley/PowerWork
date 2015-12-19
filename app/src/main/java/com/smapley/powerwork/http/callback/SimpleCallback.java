package com.smapley.powerwork.http.callback;

import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class SimpleCallback implements Callback.CommonCallback<MyResponse> {
    @Override
    public void onSuccess(MyResponse result) {
        if(result.flag.equals(MyData.SUCC)){
            onSuccess(result.data);
        }else{
            onError(result.flag,result.details);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        onFail();
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    public abstract void onFail();

    public abstract void onError(String flag,String details);

    public abstract void onSuccess(String data);
}
