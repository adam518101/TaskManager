package com.talent.taskmanager;

import com.talent.taskmanager.notification.TaskManagerService;

/**
 * Created by acmllaugh on 14-11-21.
 */
public interface Constants {

    public static final boolean IN_DEBUG_MODE = true;
    public static final String SERVICE_NAME = TaskManagerService.class.getName();
    public static final String TASK_LIST_ACTIVITY_NAME = TaskListActivity.class.getName();
    public static final String START_TASK_LIST_ACTIVITY_ACTION = "start_task_list_action";
    public static final String USER_ID = "user_id";
    public static final String TASK_MANAGER = "task_manager";
    public static final String SAVED_USER_ID = "saved_user_id";
    public static final int INVALIDATE_USER_ID = -1;

}
