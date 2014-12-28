package com.talent.taskmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.handlers.UserTaskStatusChangeHandler;
import com.coal.black.bc.socket.client.returndto.UserTaskStatusChangeResult;
import com.coal.black.bc.socket.common.UserTaskStatusCommon;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.exception.ExceptionBase;
import com.talent.taskmanager.network.NetworkState;
import com.talent.taskmanager.task.TaskDetailDialog;

import de.greenrobot.event.EventBus;


public class SingleTaskActivity extends Activity {

    private EventBus mEventBus = EventBus.getDefault();
    private TaskDto mTask;
    private TextView mTaskTitleView;
    private TextView mDetailButton;
    private AlertDialog mDetailDialog;
    private ProgressDialog mProgressDialog;
    private Toast mToast;
    private Handler mTaskStatusHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("acmllaugh1", "handleMessage (line 38): get task result.");
            Utils.dissmissProgressDialog(mProgressDialog);
            if (msg.obj instanceof UserTaskStatusChangeResult) {
                UserTaskStatusChangeResult result = (UserTaskStatusChangeResult) msg.obj;
                //TODO : Whether success or not, reload the task from server(but consider a min time for reload task).
                if (result.isSuccess()) {
                    if (msg.arg1 == UserTaskStatusCommon.HAS_READED) {
                        //We changed a task from unread to readed.
                    }
                    if (msg.arg1 == UserTaskStatusCommon.IN_DEALING) {
                        Utils.showToast(mToast,getString(R.string.task_accept_success), getApplicationContext());
                    }
                    mTask.setTaskStatus(msg.arg1);

                } else {
                    Log.d("acmllaugh1", "handleMessage (line 38): result error code : " + result.getBusinessErrorCode());
                    if (result.isBusException() && result.getBusinessErrorCode() == ExceptionBase.USER_TASK_NOT_VALID) {
                        Utils.showToast(mToast, getString(R.string.task_status_not_valid), getApplicationContext());
                        SingleTaskActivity.this.finish();
                    }
                   // Utils.showToast(mToast, getString(R.string.change_task_status_fail), getApplicationContext());
                }
            }
        }
    };
    private Menu mMenu;

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
        mMenu = menu;
        getMenuInflater().inflate(R.menu.single_task, menu);
        checkTaskStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_start_task) {
            startTask();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startTask() {
        changeTaskStatus(UserTaskStatusCommon.IN_DEALING);
    }

    private void changeTaskStatus(final int targetStatus) {
        NetworkState state = Utils.getCurrentNetworkState(this.getApplicationContext());
        if (!state.isConnected()) {
            Utils.dissmissProgressDialog(mProgressDialog);
            Utils.showToast(mToast, getString(R.string.net_work_unavailable), this.getApplicationContext());
            return;
        }
        if (mTask.getTaskStatus() >= UserTaskStatusCommon.IN_DEALING
                || targetStatus > UserTaskStatusCommon.IN_DEALING) {
            Utils.showToast(mToast, "Task state is not proper.", this.getApplicationContext());
        }
        mProgressDialog = Utils.showProgressDialog(mProgressDialog, this);
        Thread changeTaskStatusThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("acmllaugh1", "run (line 121): start change task status.");
                Log.d("acmllaugh1", "run (line 124): task id : " + mTask.getId());
                Log.d("acmllaugh1", "run (line 125): user id : " + ClientGlobal.userId);
                UserTaskStatusChangeHandler handler = new UserTaskStatusChangeHandler();
                UserTaskStatusChangeResult result = handler.changeUserTaskStatus(
                        mTask.getId(), targetStatus);
                Message msg = new Message();
                msg.obj = result;
                msg.arg1 = targetStatus;
                Log.d("acmllaugh1", "run (line 128): result : " + result.isSuccess());
                mTaskStatusHandler.sendMessage(msg);
            }
        });
        changeTaskStatusThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    /**
     * Receive TaskDTO object from task list activity.
     *
     * @param task
     */
    public void onEvent(TaskDto task) {
        if (mTaskTitleView == null) {
            mTaskTitleView = (TextView) findViewById(R.id.txt_single_task_title);
        }
        mTaskTitleView.setText(task.getVisitReason());
        mTask = task;
    }

    private void checkTaskStatus() {
        if (mTask == null) {
            return;
        }
        switch (mTask.getUserTaskStatus()) {
            case UserTaskStatusCommon.NOT_READ:
                changeTaskStatus(UserTaskStatusCommon.HAS_READED);
                break;
            case UserTaskStatusCommon.IN_DEALING:
                int count = mMenu.size();
                for (int i = 0; i < count; i++) {
                    MenuItem item = mMenu.getItem(i);
                    if (item.getItemId() == R.id.action_start_task) {
                        item.setVisible(false);
                    }
                }
                break;
        }
    }


}
