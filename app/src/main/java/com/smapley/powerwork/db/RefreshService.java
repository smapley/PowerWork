package com.smapley.powerwork.db;

import com.smapley.powerwork.application.LocalApplication;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by smapley on 15/12/20.
 */
public class RefreshService {

    private DbManager dbUtils;

    public RefreshService(){
        dbUtils= LocalApplication.getInstance().dbUtils;

    }

    public Refresh findById(int id){
        Refresh refresh=null;
        try {
            refresh=dbUtils.findById(Refresh.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return refresh;
    }

    public void saveOrUpdate(Refresh refresh){
        try {
            dbUtils.saveOrUpdate(refresh);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
