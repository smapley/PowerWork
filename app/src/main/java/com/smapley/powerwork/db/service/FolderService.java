package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.FileEntity;
import com.smapley.powerwork.db.entity.FolderEntity;
import com.smapley.powerwork.db.mode.FolderMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class FolderService {
    private static DbManager dbUtils = LocalApplication.getInstance().dbUtils;


    public static void save(FolderMode folderMode) {
        if (folderMode != null) {
            try {
                if (folderMode.getFolderEntity() != null)
                    dbUtils.saveOrUpdate(folderMode.getFolderEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (folderMode.getListFileEntities() != null && !folderMode.getListFileEntities().isEmpty())
                for (FileEntity fileEntity : folderMode.getListFileEntities()) {
                    try {
                        dbUtils.saveOrUpdate(fileEntity);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            if(folderMode.getFolderModes()!=null&&!folderMode.getFolderModes().isEmpty()){
                for(FolderMode folderMode1:folderMode.getFolderModes())
                    save(folderMode1);
            }
        }
    }

    public FolderMode findById(int folId) {
        FolderMode folderMode = new FolderMode();

        //添加FolderEntity
        try {
            folderMode.setFolderEntity(dbUtils.findById(FolderEntity.class, folId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加FileEntity
        try {
            folderMode.setListFileEntities(dbUtils.selector(FileEntity.class).where("fol_id", "=", folId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return folderMode;
    }

    public List<FolderMode> findByProId(int proId) {
        List<FolderMode> list = new ArrayList<>();
        List<FolderEntity> folderEntities = null;
        try {
            folderEntities = dbUtils.selector(FolderEntity.class).where("pro_id", "=", proId).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (folderEntities != null && !folderEntities.isEmpty())
            for (FolderEntity folderEntity : folderEntities) {
                list.add(findById(folderEntity.getFol_id()));
            }

        return list;
    }
}
