package com.smapley.powerwork.http;

import com.smapley.powerwork.entity.User_Entity;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class BaseParams extends RequestParams {

    public BaseParams(String url,User_Entity user_entity){
        super(url);
        addBodyParameter("user_id", user_entity.getUse_id() + "");
        addBodyParameter("skey", user_entity.getSkey());
    }

}
