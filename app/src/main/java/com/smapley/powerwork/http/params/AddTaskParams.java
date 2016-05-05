package com.smapley.powerwork.http.params;

import com.alibaba.fastjson.JSON;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.db.entity.TasUseEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.utils.MyData;

import java.io.File;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class AddTaskParams extends BaseParams {


    public AddTaskParams(UserEntity userEntity) {
        super(MyData.URL_AddTask, userEntity);
    }

    public AddTaskParams setName(String name){
        addBodyParameter("name", name);
        return this;
    }
    public AddTaskParams setEndtime(Long endtime){
        addBodyParameter("endtime", endtime+"");
        return this;
    }
    public AddTaskParams setProId(int pro_id){
        addBodyParameter("pro_id",pro_id+"");
        return this;
    }

    public AddTaskParams setTasUse(List<TasUseEntity> listTasUse){
        addBodyParameter("tasuse", JSON.toJSONString(listTasUse));
        return this;
    }

    public AddTaskParams setList(List<NoteDetailsEntity> list){
        addBodyParameter("size", list.size() + "");
        setMultipart(true);
        for (int i = 0; i < list.size(); i++) {
            addBodyParameter("type" + i, list.get(i).getType() + "");
            addBodyParameter("text" + i, list.get(i).getText());
            String path = list.get(i).getPath();
            if (path != null && !path.isEmpty()) {
                addBodyParameter("file" + i, new File(path));
            }
            addBodyParameter("length" + i, list.get(i).getLength() + "");
        }
        return this;
    }



}
