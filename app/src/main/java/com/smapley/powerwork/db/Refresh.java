package com.smapley.powerwork.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/12/20.
 */
@Table(name = "Refresh")
public class Refresh {

    @Column(name = "use_id", isId = true, autoGen = false)
    private int use_id;
    @Column(name = "TaskList")
    private long TaskList = 0;
    @Column(name = "ProjectList")
    private long ProjectList=0;
    @Column(name = "NoteList")
    private long NoteList=0;
    @Column(name = "MessageList")
    private long MessageList=0;


    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public long getTaskList() {
        return TaskList;
    }

    public void setTaskList(long taskList) {
        TaskList = taskList;
    }

    public long getProjectList() {
        return ProjectList;
    }

    public void setProjectList(long projectList) {
        ProjectList = projectList;
    }

    public long getNoteList() {
        return NoteList;
    }

    public void setNoteList(long noteList) {
        NoteList = noteList;
    }

    public long getMessageList() {
        return MessageList;
    }

    public void setMessageList(long MessageList) {
        this.MessageList = MessageList;
    }
}
