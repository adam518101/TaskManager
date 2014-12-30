package com.talent.taskmanager.dada;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chris on 14-12-29.
 */
public class DBHelper extends SQLiteOpenHelper{
    /*	Database	*/
    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;
    /*	Tables	*/
    public static final String TABLE_FILES = "upload_files";
    /*	Columns	*/
    public static final String FIELD_ID = "_id";
    public static final String FIELD_TASK_ID = "task_id";
    public static final String FIELD_USER_ID = "uset_id";
    // 0: not picture;  1: is picture
    public static final String FIELD_IS_PICTURE = "is_picture";
    public static final String FIELD_FILE_PATH = "file_path";
    // 0: unfinished;  1: finished
    public static final String FIELD_RESULT = "upload_result";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format("CREATE TABLE %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER," +
                        " %s INTEGER, %s TEXT, %s INTEGER)",
                TABLE_FILES,
                FIELD_ID, FIELD_TASK_ID, FIELD_USER_ID,
                FIELD_IS_PICTURE, FIELD_FILE_PATH, FIELD_RESULT);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_FILES;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
