package com.manisai.myapplication;

import android.provider.BaseColumns;

public final class TaskContract {

    private TaskContract() {}

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_DETAILS = "task_details";
        public static final String COLUMN_NAME_PRIORITY = "priority";
    }
}
