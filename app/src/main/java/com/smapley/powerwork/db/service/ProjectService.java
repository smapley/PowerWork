package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.ProUseEntity;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.mode.DynamicMode;
import com.smapley.powerwork.db.mode.FolderMode;
import com.smapley.powerwork.db.mode.ProUseMode;
import com.smapley.powerwork.db.mode.ProjectMode;
import com.smapley.powerwork.db.mode.TaskMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class ProjectService {
    private DbManager dbUtils;

    public ProjectService() {
        dbUtils = LocalApplication.getInstance().dbUtils;
    }

    public void save(ProjectMode projectMode) {
        if (projectMode != null) {
            try {
                if (projectMode.getProjectEntity() != null)
                    dbUtils.saveOrUpdate(projectMode.getProjectEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            for (ProUseMode proUseMode : projectMode.getListProUseModes()) {
                new ProUseService().save(proUseMode);
            }
            for (TaskMode taskMode : projectMode.getListTaskModes()) {
                new TaskService().save(taskMode);
            }
            for (DynamicMode dynamicMode : projectMode.getListDynamicModes()) {
                new DynamicService().save(dynamicMode);
            }
            for (FolderMode folderMode : projectMode.getListFolderModes()) {
                new FolderService().save(folderMode);
            }
        }
    }

    public ProjectMode findById(int proId) {
        ProjectMode projectMode = new ProjectMode();
        //添加ProjectEntity
        ProjectEntity projectEntity = null;
        try {
            projectEntity = dbUtils.findById(ProjectEntity.class, proId);
            projectMode.setProjectEntity(projectEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加ProUseMode
        projectMode.setListProUseModes(new ProUseService().findByProId(proId));

        //添加DynamicMode
        projectMode.setListDynamicModes(new DynamicService().findByProId(proId));

        //添加TaskMode
        projectMode.setListTaskModes(new TaskService().findByProId(proId));

        //添加FolderMode
        projectMode.setListFolderModes(new FolderService().findByProId(proId));

        return projectMode;
    }

    public List<ProjectMode> findByUseId(int useId) {
        List<ProjectMode> list = new ArrayList<>();
        List<ProUseEntity> proUseEntityList = new ArrayList<>();
        try {
            proUseEntityList = dbUtils.selector(ProUseEntity.class).where("use_id", "=", useId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        for (ProUseEntity proUseEntity : proUseEntityList) {
            list.add(findById(proUseEntity.getPro_id()));
        }
        return list;
    }
}
