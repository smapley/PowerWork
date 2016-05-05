package com.smapley.powerwork.db.services;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.UserEntity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by smapley on 15/12/18.
 */
public class UserService {
    private static DbManager dbUtils = LocalApplication.getInstance().dbUtils;

    public static void save(UserEntity userEntity) {
        if (userEntity != null) {
            try {
                dbUtils.saveOrUpdate(userEntity);
            } catch (DbException e) {
                e.printStackTrace();
            }


        }
    }
}
