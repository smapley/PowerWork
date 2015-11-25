package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item5_Folder_Mode implements BaseMode {

    private int type = 2;

    private int folderType = 1;

    private String name;

    public int getFolderType() {
        return folderType;
    }

    public void setFolderType(int folderType) {
        this.folderType = folderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getType() {
        return type;
    }
}
