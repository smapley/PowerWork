package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.TaskDetailsEntity;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.db.mode.TasUseMode;
import com.smapley.powerwork.db.mode.TaskMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class TaskService {
    private DbManager dbUtils;

    public TaskService() {
        dbUtils = LocalApplication.getInstance().dbUtils;
    }

    public void save(TaskMode taskMode) {
        if (taskMode != null) {
            try {
                if (taskMode.getTaskEntity() != null)
                    dbUtils.saveOrUpdate(taskMode.getTaskEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            for (TaskDetailsEntity taskDetailsEntity : taskMode.getListTaskDetailsEntities()) {
                try {
                    if (taskDetailsEntity != null)
                        dbUtils.saveOrUpdate(taskDetailsEntity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            for (TasUseMode tasUseMode : taskMode.getListTasUseModes()) {
                new TasUseService().save(tasUseMode);
            }
        }
    }

    public TaskMode findById(int tasId) {
        TaskMode taskMode = new TaskMode();

        //添加TaskEntity
        try {
            taskMode.setTaskEntity(dbUtils.findById(TaskEntity.class, tasId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加TaskDetailsEntity
        try {
            taskMode.setListTaskDetailsEntities(dbUtils.selector(TaskDetailsEntity.class).where("tas_id", "=", tasId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加TasUseMode
        taskMode.setListTasUseModes(new TasUseService().findByTasId(tasId));

        return taskMode;
    }


    public List<TaskMode> findByProId(int proId) {
        List<TaskMode> list = new ArrayList<>();
        List<TaskEntity> taskEntities = null;
        try {
            taskEntities=dbUtils.selector(TaskEntity.class).where("pro_id","=",proId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        for(TaskEntity taskEntity:taskEntities){
            list.add(findById(taskEntity.getTas_id()));
        }

        return list;
    }
}
