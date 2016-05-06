package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.EditText;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.db.entity.TaskDetailsEntity;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Text_Holder extends BaseHolder {

    private EditText add_et_text;

    public Add_Text_Holder(View view) {
        super(view);
        add_et_text = (EditText) view.findViewById(R.id.add_et_text);
    }


    public void setData(final AddTaskAdapter adapter, final TaskDetailsEntity mode, final int position, boolean edit) {
        add_et_text.requestFocus();
        add_et_text.setText(mode.getText());
        add_et_text.setEnabled(edit);
        add_et_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mode.setText(add_et_text.getText().toString());
            }
        });


    }
}
