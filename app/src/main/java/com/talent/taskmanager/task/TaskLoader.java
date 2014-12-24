package com.talent.taskmanager.task;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.coal.black.bc.socket.client.handlers.TaskQueryHandler;
import com.coal.black.bc.socket.client.returndto.TaskQueryResult;
import com.coal.black.bc.socket.dto.TaskDto;

import java.util.ArrayList;

/**
 * Created by acmllaugh on 14-11-20.
 */
public class TaskLoader extends AsyncTaskLoader<ArrayList<TaskDto>> {

    private ArrayList<TaskDto> mTaskList;
    private TaskObserver mTaskObserver;

    public TaskLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<TaskDto> loadInBackground() {
       //TODO : connect to server and get all the tasks data.
       int[] status = new int[] { 1, 2, 3 };// 状态
       TaskQueryHandler taskQueryHandler = new TaskQueryHandler();
       TaskQueryResult result = taskQueryHandler.qryTasks(status);
       return (ArrayList<TaskDto>) result.getTaskList();
    }

    @Override
    protected void onStartLoading() {
        if (mTaskList != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mTaskList);
        }

        if (mTaskObserver == null) {
            mTaskObserver = new TaskObserver();
        }

        if (takeContentChanged() || mTaskList == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStartLoading();

        if (mTaskList != null) {
            mTaskList = null;
        }

        if (mTaskObserver != null) {
            mTaskObserver = null;
        }

    }

    @Override
    public void onCanceled(ArrayList<TaskDto> data) {
        super.onCanceled(data);
    }

    @Override
    public void deliverResult(ArrayList<TaskDto> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            return;
        }

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

    }

}
