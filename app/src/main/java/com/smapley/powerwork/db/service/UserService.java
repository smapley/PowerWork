package com.smapley.powerwork.db.service;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.mode.NoteMode;
import com.smapley.powerwork.db.mode.ProjectMode;
import com.smapley.powerwork.db.mode.UserMode;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by smapley on 15/12/18.
 */
public class UserService {
    private DbManager dbUtils;

    public UserService() {
        dbUtils = LocalApplication.getInstance().dbUtils;
    }

    public void save(UserMode userMode) {
        if (userMode != null) {
            try {
                if (userMode.getUserEntity() != null)
                    dbUtils.saveOrUpdate(userMode.getUserEntity());
            } catch (DbException e) {
                e.printStackTrace();
            }

            for (NoteMode noteMode : userMode.getListNoteModes()) {
                new NoteService().save(noteMode);
            }

            for (ProjectMode projectMode : userMode.getListProjectModes()) {
                new ProjectService().save(projectMode);
            }
        }
    }
}
