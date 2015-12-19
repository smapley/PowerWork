package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.TasUseEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.db.mode.TasUseMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class TasUseService {
    private DbManager dbUtils;

    public TasUseService() {
        dbUtils = LocalApplication.getInstance().dbUtils;
    }

    public void save(TasUseMode tasUseMode) {
        if (tasUseMode != null) {
            try {
                if (tasUseMode.getTasUseEntity() != null)
                    dbUtils.replace(tasUseMode.getTasUseEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            try {
                if (tasUseMode.getUserEntity() != null)
                    dbUtils.saveOrUpdate(tasUseMode.getUserEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public TasUseMode findById(int id) {
        TasUseMode tasUseMode = new TasUseMode();

        //添加TasUseEntity
        try {
            tasUseMode.setTasUseEntity(dbUtils.findById(TasUseEntity.class, id));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加UserEntity
        try {
            tasUseMode.setUserEntity(dbUtils.findById(UserEntity.class, tasUseMode.getTasUseEntity().getUse_id()));
        } catch (DbException e) {
            e.printStackTrace();
        }

        return tasUseMode;
    }

    public List<TasUseMode> findByTasId(int tasId) {
        List<TasUseMode> list = new ArrayList<>();
        List<TasUseEntity> tasUseEntities = null;
        try {
            tasUseEntities = dbUtils.selector(TasUseEntity.class).where("tas_id", "=", tasId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        for (TasUseEntity tasUseEntity : tasUseEntities) {

            list.add(findById(tasUseEntity.getId()));
        }

        return list;

    }
}
