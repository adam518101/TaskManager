package com.talent.taskmanager;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.talent.taskmanager.task.TaskLoader;
import com.coal.black.bc.socket.dto.TaskDto;
import com.github.androidprogresslayout.ProgressLayout;

import java.util.ArrayList;

/**
 * Created by acmllaugh on 14-11-20.
 */
public class TaskLoaderCallback implements LoaderManager.LoaderCallbacks<ArrayList<TaskDto>> {

    private TextView mEmptyView;
    private Activity mActivity;
    private ListView mTaskList;
    private ArrayAdapter mAdapter;
    private ProgressLayout mProgressLayout;

    public TaskLoaderCallback(Activity activity, ListView taskListView, ArrayAdapter adapter) {
        mActivity = activity;
        mTaskList = taskListView;
        mAdapter = adapter;
        if (mActivity instanceof TaskListActivity) {
            mProgressLayout = ((TaskListActivity) mActivity).getProgressLayout();
            mEmptyView = (TextView) mActivity.findViewById(R.id.txt_empty);
        }
    }

    @Override
    public Loader<ArrayList<TaskDto>> onCreateLoader(int i, Bundle bundle) {
//        Utils.log("TaskLoaderCallback:", "create progress and loader.");
        displayProgress(true);
        return new TaskLoader(mActivity.getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TaskDto>> arrayListLoader, ArrayList<TaskDto> tasks) {
        if (tasks != null) {
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
            showEmptyView(tasks.size() < 1);
        }
        displayProgress(false);
    }

    private void showEmptyView(boolean visiable) {
        Utils.log("acmllaugh1:", "set empty view : " + visiable);
        if (mEmptyView == null) {
            Utils.log("acmllaugh1:", "Empty view is null.");
            return;
        }
        if (visiable) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<TaskDto>> arrayListLoader) {

    }

    private void displayProgress(boolean showProgress) {
        if (mProgressLayout != null) {
//            Utils.log("TaskLoaderCallback:", "show progress : " + showProgress);
            ArrayList<Integer> skipIDs = new ArrayList<Integer>();
            skipIDs.add(R.id.txt_empty);
            mProgressLayout.setProgress(showProgress, skipIDs);
        }
    }
}
