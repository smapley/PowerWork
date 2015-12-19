package com.smapley.powerwork.http.params;

import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.db.entity.UserBaseEntity;
import com.smapley.powerwork.utils.MyData;

import java.io.File;
import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public class AddNoteParams extends BaseParams {


    public AddNoteParams(UserBaseEntity userBaseEntity) {
        super(MyData.URL_AddNote, userBaseEntity);
    }

    public AddNoteParams setName(String name){
        addBodyParameter("name", name);
        return this;
    }
    public AddNoteParams setAlarm(Long alarm){
        addBodyParameter("alarm", alarm + "");
        return this;
    }
    public AddNoteParams setList(List<NoteDetailsEntity> list){
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
