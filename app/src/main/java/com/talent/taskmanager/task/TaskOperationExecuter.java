package com.talent.taskmanager.task;

import android.graphics.Bitmap;

import com.coal.black.bc.socket.dto.TaskDto;

/**
 * Created by acmllaugh on 14-11-24.
 */
public enum  TaskOperationExecuter {
    INSTANCE;

    public static TaskOperationExecuter getInstance() {
        return INSTANCE;
    }

    public boolean updateImg(TaskDto task, Bitmap bitmap) {
        boolean updateResult = false;
        return updateResult;
    }
}
