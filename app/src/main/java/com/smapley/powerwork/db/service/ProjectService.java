package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.ProUseEntity;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.modes.ProUseMode;
import com.smapley.powerwork.db.modes.ProjectMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class ProjectService {
    private static DbManager dbUtils = LocalApplication.getInstance().dbUtils;

    public static void save(ProjectMode projectMode) {
        if (projectMode != null) {
            try {
                if (projectMode.getProjectEntity() != null)
                    dbUtils.saveOrUpdate(projectMode.getProjectEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (projectMode.getListProUseModes() != null && !projectMode.getListProUseModes().isEmpty())
                for (ProUseMode proUseMode : projectMode.getListProUseModes()) {
                    new ProUseService().save(proUseMode);
                }
        }
    }

    public static ProjectMode findById(int proId) {
        ProjectMode projectMode = new ProjectMode();
        //添加ProjectEntity
        try {
            projectMode.setProjectEntity(dbUtils.findById(ProjectEntity.class, proId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加ProUseMode
        projectMode.setListProUseModes(new ProUseService().findByProId(proId));


        return projectMode;
    }

    public static List<ProjectMode> findByUseId(int useId) {
        List<ProjectMode> list = new ArrayList<>();
        List<ProUseEntity> proUseEntityList = new ArrayList<>();
        try {
            proUseEntityList = dbUtils.selector(ProUseEntity.class).where("use_id", "=", useId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (proUseEntityList != null && !proUseEntityList.isEmpty())
            for (ProUseEntity proUseEntity : proUseEntityList) {
                list.add(findById(proUseEntity.getPro_id()));
            }
        return list;
    }

    public static List<ProjectEntity> findEntityByUseId(int useId) {
        List<ProjectEntity> list = new ArrayList<>();
        List<ProUseEntity> proUseEntities = ProUseService.findEntityByUseId(useId);
        if (proUseEntities != null && !proUseEntities.isEmpty())
            for (ProUseEntity proUseEntity : proUseEntities)
                try {
                    list.add(dbUtils.findById(ProjectEntity.class, proUseEntity.getPro_id()));
                } catch (DbException e) {
                    e.printStackTrace();
                }

        Collections.reverse(list);
        return list;
    }
}
