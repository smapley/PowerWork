package com.smapley.powerwork.http.params;

import com.smapley.powerwork.db.entity.UserEntity;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class BaseParams extends RequestParams {

    public BaseParams(String url, UserEntity userEntity) {
        super(url);
        if (userEntity != null) {
            addBodyParameter("userId",userEntity.getUseId()+"");
        }
    }
}
