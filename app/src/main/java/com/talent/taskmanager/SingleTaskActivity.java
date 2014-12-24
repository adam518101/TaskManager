package com.talent.taskmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.talent.taskmanager.task.TaskDetailDialog;
import com.coal.black.bc.socket.dto.TaskDto;

import de.greenrobot.event.EventBus;


public class SingleTaskActivity extends Activity {

    private EventBus mEventBus = EventBus.getDefault();
    private TaskDto mTask;
    private TextView mTaskTitleView;
    private TextView mDetailButton;
    private AlertDialog mDetailDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        initVariables();
        registerToEventBus();
    }

    private void initVariables() {
        mDetailButton = (TextView) findViewById(R.id.btn_task_detail);
        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailDialog == null) {
                    mDetailDialog = new TaskDetailDialog(SingleTaskActivity.this, mTask);
                }
                if (!mDetailDialog.isShowing()) {
                    mDetailDialog.show();
                }
            }
        });
    }

    private void registerToEventBus() {
        if (!mEventBus.isRegistered(this)) {
            //mEventBus.register(this);
            mEventBus.registerSticky(this);
        }
    }

    private void unRegisterEventBus() {
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    public void onEvent(TaskDto task) {
        if (mTaskTitleView == null) {
            mTaskTitleView = (TextView) findViewById(R.id.txt_single_task_title);
        }
        mTaskTitleView.setText(task.getVisitReason());
        mTask = task;
    }


}
