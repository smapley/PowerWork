package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.ProUseEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.db.modes.ProUseMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class ProUseService {
    private static DbManager dbUtils= LocalApplication.getInstance().dbUtils;


    public void save(ProUseMode proUseMode) {
        if (proUseMode != null) {
            try {
                if (proUseMode.getUserEntity() != null)
                    dbUtils.replace(proUseMode.getProUseEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            try {
                if (proUseMode.getUserEntity() != null)
                    dbUtils.saveOrUpdate(proUseMode.getUserEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public ProUseMode findById(int id) {
        ProUseMode proUseMode = new ProUseMode();

        //添加ProUseEntity
        try {
            proUseMode.setProUseEntity(dbUtils.findById(ProUseEntity.class, id));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加UserEntity
        try {
            if (proUseMode.getProUseEntity() != null)
                proUseMode.setUserEntity(dbUtils.findById(UserEntity.class, proUseMode.getProUseEntity().getUse_id()));
        } catch (DbException e) {
            e.printStackTrace();
        }

        return proUseMode;
    }

    public List<ProUseMode> findByProId(int proId) {
        List<ProUseMode> list = new ArrayList<>();
        List<ProUseEntity> proUseEntityList = null;
        try {
            proUseEntityList = dbUtils.selector(ProUseEntity.class).where("pro_id", "=", proId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (proUseEntityList != null && !proUseEntityList.isEmpty())
            for (ProUseEntity proUseEntity : proUseEntityList) {
                list.add(findById(proUseEntity.getId()));
            }

        return list;
    }

    public static  List<ProUseEntity> findEntityByUseId(int useId){
        try {
            return dbUtils.selector(ProUseEntity.class).where("use_id","=",useId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

}
