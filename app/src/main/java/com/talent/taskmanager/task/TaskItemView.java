package com.talent.taskmanager.task;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.talent.taskmanager.R;
import com.coal.black.bc.socket.dto.TaskDto;

/**
 * Created by acmllaugh on 14-11-20.
 */
public class TaskItemView extends RelativeLayout {


    //private ImageView mTaskIconImgView;
    private TextView mTaskTitleTextView;
    private TextView mPersonNameTextView;
    private TextView mContactInfoTextView;
    private TextView mAddressTextView;

    public TaskItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       // mTaskIconImgView = (ImageView) findViewById(R.id.img_task_icon);
        mTaskTitleTextView = (TextView) findViewById(R.id.txt_task_title);
        mPersonNameTextView = (TextView) findViewById(R.id.txt_person_name);
        mContactInfoTextView = (TextView) findViewById(R.id.txt_contact_info);
        mAddressTextView = (TextView) findViewById(R.id.txt_address);
    }

    public void bindModel(TaskDto task) {
        mTaskTitleTextView.setText(task.getVisitReason());
        mPersonNameTextView.setText(task.getName());
        mContactInfoTextView.setText(task.getContactInfo());
        mAddressTextView.setText(task.getAddress());
    }

}
