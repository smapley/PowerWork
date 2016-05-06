package com.smapley.powerwork.utils;

/**
 * Created by smapley on 15/10/22.
 */
public class MyData {

    public static final String SUCC = "succeed";
    public static final String FAIL = "fail";
    public static final String OutLogin = "OutLogin";


    public static final String SP_USER = "user";
    public static final String SP_SET = "set";

    public static final String File_Log = "/log";
    public static final String File_Log_Crash = File_Log + "/crash.log";
    public static final String File_Audio = "/audio";

    private final static String BASE_URL = "http://115.28.213.161:8080/PowerWorkService/";
//    private final static String BASE_URL = "http://172.23.100.4:8080/PowerWorkService/";
    public final static String URL_PIC = BASE_URL + "upload/pic/";
    public final static String URL_File = BASE_URL + "upload/file/";
    public final static String URL_NOTE = BASE_URL + "upload/note/";
    public final static String URL_LOGIN = BASE_URL + "Login";
    public final static String URL_REGISTER = BASE_URL + "Register";
    public final static String URL_UserPicUpLoad = BASE_URL + "UserPicUpLoad";
    public final static String URL_Account = BASE_URL + "Account";
    public final static String URL_Feedback = BASE_URL + "Feedback";
    public final static String URL_AddProject = BASE_URL + "AddProject";
    public final static String URL_ProjectList = BASE_URL + "ProjectList";
    public final static String URL_PTask = BASE_URL + "PTask";
    public final static String URL_AddNote = BASE_URL + "AddNote";
    public final static String URL_AddTask = BASE_URL + "AddTask";
    public final static String URL_TaskList = BASE_URL + "TaskList";
    public final static String URL_TeamList = BASE_URL + "TeamList";
    public final static String URL_NoteList = BASE_URL + "NoteList";
    public final static String URL_DynamicList = BASE_URL + "DynamicList";
    public final static String URL_OtherTaskList = BASE_URL + "OtherTaskList";
    public final static String URL_FolderList = BASE_URL + "FolderList";
    public final static String URL_FileList = BASE_URL + "FileList";
    public final static String URL_AddFolder = BASE_URL + "Add";
    public final static String URL_AddFile = BASE_URL + "AddFile";
    public final static String URL_SearchProject = BASE_URL + "SearchProject";
    public final static String URL_MessageList = BASE_URL + "MessageList";
    public final static String URL_SendMessage = BASE_URL + "SendMessage";


    public final static String PIC_FILE = URL_PIC + "base/file.png";
    public final static String PIC_TEAM = URL_PIC + "base/team.png";
    public final static String PIC_PIC = URL_PIC + "base/pic.png";
    public final static String PIC_TASK = URL_PIC + "base/task.png";
    public final static String PIC_MAN = URL_PIC + "base/man.png";

}
