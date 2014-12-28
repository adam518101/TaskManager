package com.talent.taskmanager.task;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
    private TextView mTaskStatusTextView;
    private TextView mPersonNameTextView;
    private TextView mContactInfoTextView;
    private TextView mAddressTextView;
    private Context mContext;

    public TaskItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context.getApplicationContext();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       // mTaskIconImgView = (ImageView) findViewById(R.id.img_task_icon);
        mTaskTitleTextView = (TextView) findViewById(R.id.txt_task_title);
        mTaskStatusTextView = (TextView) findViewById(R.id.txt_task_status);
        mPersonNameTextView = (TextView) findViewById(R.id.txt_person_name);
        mContactInfoTextView = (TextView) findViewById(R.id.txt_contact_info);
        mAddressTextView = (TextView) findViewById(R.id.txt_address);
    }

    public void bindModel(TaskDto task) {
        mTaskTitleTextView.setText(task.getVisitReason());
        mPersonNameTextView.setText(task.getName());
        mContactInfoTextView.setText(task.getContactInfo());
        mAddressTextView.setText(task.getAddress());
        showTaskStatus(task);
    }

    private void showTaskStatus(TaskDto task) {
        boolean needBoldText = false;
        switch (task.getUserTaskStatus()) {
            case 1:
                mTaskStatusTextView.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
                mTaskStatusTextView.setText(mContext.getString(R.string.un_read));
                needBoldText = true;
                break;
            case 2:
                mTaskStatusTextView.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
                mTaskStatusTextView.setText(mContext.getString(R.string.has_been_read));
                break;
            case 3:
                mTaskStatusTextView.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_light));
                mTaskStatusTextView.setText(mContext.getString(R.string.task_processing));
                break;
        }
        setTextBold(needBoldText);
    }

    private void setTextBold(boolean needBold) {
        for (int i = getChildCount()-1; i >= 0; i--) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                if (view.getId() == R.id.txt_task_title) {
                    Log.d("acmllaugh1", "task title is processed.");
                }
                TextView textView = (TextView) view;
                TextPaint tp = textView.getPaint();
                tp.setFakeBoldText(needBold);
            }
        }
    }

}
