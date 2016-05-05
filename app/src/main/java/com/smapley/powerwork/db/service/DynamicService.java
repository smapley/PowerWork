package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.DiscussEntity;
import com.smapley.powerwork.db.entity.DynamicEntity;
import com.smapley.powerwork.db.entity.PraiseEntity;
import com.smapley.powerwork.db.mode.DynamicMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class DynamicService {
    private static DbManager dbUtils= LocalApplication.getInstance().dbUtils;

    public static void save(DynamicMode dynamicMode) {
        if (dynamicMode != null) {
            try {
                if (dynamicMode.getDynamicEntity() != null)
                    dbUtils.saveOrUpdate(dynamicMode.getDynamicEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (dynamicMode.getListDiscussEntities() != null && !dynamicMode.getListDiscussEntities().isEmpty())
                for (DiscussEntity discussEntity : dynamicMode.getListDiscussEntities()) {
                    try {
                        dbUtils.saveOrUpdate(discussEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            if (dynamicMode.getListPraiseEntities() != null && !dynamicMode.getListPraiseEntities().isEmpty())
                for (PraiseEntity praiseEntity : dynamicMode.getListPraiseEntities()) {
                    try {
                        dbUtils.saveOrUpdate(praiseEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public DynamicMode findById(int dynId) {
        DynamicMode dynamicMode = new DynamicMode();
        //添加DynamicEntity
        try {
            dynamicMode.setDynamicEntity(dbUtils.findById(DynamicEntity.class, dynId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加DiscussEntity
        try {
            dynamicMode.setListDiscussEntities(dbUtils.selector(DiscussEntity.class).where("dyn_id", "=", dynId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加PraiseEntity
        try {
            dynamicMode.setListPraiseEntities(dbUtils.selector(PraiseEntity.class).where("dyn_id", "=", dynId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }

        return dynamicMode;
    }

    public List<DynamicMode> findByProId(int proId) {
        List<DynamicMode> list = new ArrayList<>();
        List<DynamicEntity> dynamicEntities = null;
        try {
            dynamicEntities = dbUtils.selector(DynamicEntity.class).where("pro_id", "=", proId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (dynamicEntities != null && !dynamicEntities.isEmpty())
            for (DynamicEntity dynamicEntity : dynamicEntities) {
                list.add(findById(dynamicEntity.getDyn_id()));
            }

        return list;

    }
}
