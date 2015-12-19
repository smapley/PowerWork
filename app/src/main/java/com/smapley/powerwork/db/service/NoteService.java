package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.db.entity.NoteEntity;
import com.smapley.powerwork.db.mode.NoteMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by smapley on 15/12/18.
 */
public class NoteService {
    private DbManager dbUtils;

    public NoteService() {
        dbUtils = LocalApplication.getInstance().dbUtils;
    }

    public void save(NoteMode noteMode) {
        if (noteMode != null) {
            try {
                if (noteMode.getNoteEntity() != null)
                    dbUtils.saveOrUpdate(noteMode.getNoteEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }
            for (NoteDetailsEntity noteDetailsEntity : noteMode.getListnoteDetailsEntities()) {
                try {
                    if (noteDetailsEntity != null)
                        dbUtils.saveOrUpdate(noteDetailsEntity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public NoteMode findById(int notId){
        NoteMode noteMode=new NoteMode();

        //添加NoteEntity
        try {
            noteMode.setNoteEntity(dbUtils.findById(NoteEntity.class,notId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //添加NoteDetailsEntity
        try {
            noteMode.setListnoteDetailsEntities(dbUtils.selector(NoteDetailsEntity.class).where("not_id","=",notId).findAll());
        } catch (DbException e) {
            e.printStackTrace();
        }

        return noteMode;
    }
}
