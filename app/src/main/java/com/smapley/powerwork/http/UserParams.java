package com.smapley.powerwork.http;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class UserParams extends RequestParams {

    public UserParams(String url){
        super(url);
    }

    public String username;
    public String password;
    public String phone;
}
