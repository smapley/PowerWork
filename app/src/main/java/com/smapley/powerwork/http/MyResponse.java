package com.smapley.powerwork.http;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by smapley on 15/11/22.
 */
@HttpResponse(parser = ResultResponseParser.class)
public class MyResponse {

    public String flag;
    public String details;
    public String data;

}
