package com.smapley.powerwork.http;

import com.lidroid.xutils.http.RequestParams;
import com.smapley.powerwork.entity.User_Entity;

/**
 * Created by smapley on 15/11/20.
 */
public class MyRequstParams extends RequestParams {

    public MyRequstParams(User_Entity user_entity) {
        addBodyParameter("user_id", user_entity.getUse_id() + "");
        addBodyParameter("skey", user_entity.getSkey());
    }
}
