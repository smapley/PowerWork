package com.smapley.powerwork.http;

import com.smapley.powerwork.entity.UserEntity;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class BaseParams extends RequestParams {

    public BaseParams(String url,UserEntity user_entity){
        super(url);
        addBodyParameter("user_id", user_entity.getUseId() + "");
        addBodyParameter("skey", user_entity.getSkey());
    }

}
