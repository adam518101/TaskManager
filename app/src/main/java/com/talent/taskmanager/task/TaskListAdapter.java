package com.talent.taskmanager.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.talent.taskmanager.R;
import com.coal.black.bc.socket.dto.TaskDto;

import java.util.ArrayList;

/**
 * Created by acmllaugh on 14-11-20.
 */
public class TaskListAdapter extends ArrayAdapter<TaskDto> {

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<TaskDto> mTaskList;

    public TaskListAdapter(Context context, int resource, ArrayList<TaskDto> taskList) {
        super(context, resource, taskList);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            mTaskList = taskList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: add getview content.
        TaskDto task = mTaskList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_task, null);
        }
        TaskItemView view = (TaskItemView) convertView;
        view.bindModel(task);
        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mTaskList == null || mTaskList.size() == 0;
    }
}
