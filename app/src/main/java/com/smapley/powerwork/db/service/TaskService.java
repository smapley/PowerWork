package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.TasUseEntity;
import com.smapley.powerwork.db.entity.TaskDetailsEntity;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.db.modes.TaskMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class TaskService {
    private static DbManager dbUtils= LocalApplication.getInstance().dbUtils;


    public static void save(TaskMode taskMode) {
        if (taskMode != null) {
            try {
                if (taskMode.getTaskEntity() != null)
                    dbUtils.saveOrUpdate(taskMode.getTaskEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (taskMode.getListTaskDetailsEntities() != null && !taskMode.getListTaskDetailsEntities().isEmpty())
                for (TaskDetailsEntity taskDetailsEntity : taskMode.getListTaskDetailsEntities()) {
                    try {
                        dbUtils.saveOrUpdate(taskDetailsEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            if (taskMode.getTasUseEntities() != null && !taskMode.getTasUseEntities().isEmpty())
                for (TasUseEntity tasUseEntity : taskMode.getTasUseEntities()) {
                    try {
                        dbUtils.saveOrUpdate(tasUseEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
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
        try {
            taskMode.setTasUseEntities(dbUtils.selector(TasUseEntity.class).where("tas_id","=",tasId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }

        return taskMode;
    }


    public List<TaskMode> findByProId(int proId) {
        List<TaskMode> list = new ArrayList<>();
        List<TaskEntity> taskEntities = null;
        try {
            taskEntities = dbUtils.selector(TaskEntity.class).where("pro_id", "=", proId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (taskEntities != null && !taskEntities.isEmpty())
            for (TaskEntity taskEntity : taskEntities) {
                list.add(findById(taskEntity.getTas_id()));
            }

        return list;
    }

    public static List<TaskEntity> findByUseId(int useId) {
        List<TaskEntity> list = new ArrayList<>();
        List<TasUseEntity> tasUseEntities = null;
        try {
            tasUseEntities = dbUtils.selector(TasUseEntity.class).where("use_id", "=", useId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (tasUseEntities != null && !tasUseEntities.isEmpty()) {
            for (TasUseEntity tasUseEntity : tasUseEntities) {
                try {
                    list.add(dbUtils.findById(TaskEntity.class, tasUseEntity.getTas_id()));
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    public static List<TaskEntity> findByUseId(int useId,long time) {
        List<TaskEntity> list = new ArrayList<>();
        List<TasUseEntity> tasUseEntities = null;
        try {
            tasUseEntities = dbUtils.selector(TasUseEntity.class).where("use_id", "=", useId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (tasUseEntities != null && !tasUseEntities.isEmpty()) {
            for (TasUseEntity tasUseEntity : tasUseEntities) {
                try {
                    list.addAll(dbUtils.selector(TaskEntity.class)
                            .where("progress", "<", "100")
                            .and("tas_id", "=", tasUseEntity.getTas_id())
                            .and("end_date","<",time).findAll());
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.reverse(list);
        return list;
    }
}
