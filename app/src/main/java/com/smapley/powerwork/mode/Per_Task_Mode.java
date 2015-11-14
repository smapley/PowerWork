package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Mode extends BaseMode {

    private final int type = 2;

    private boolean check=false;

    private boolean isOpen=false;

    private String name;

    private String time;

    private Per_Task_Details_Mode per_task_details_mode;

    public Per_Task_Details_Mode getPer_task_details_mode() {
        return per_task_details_mode;
    }

    public void setPer_task_details_mode(Per_Task_Details_Mode per_task_details_mode) {
        this.per_task_details_mode = per_task_details_mode;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isCheck() {
        return check;
    }


    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
