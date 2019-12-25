package com.example.list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "list.db";
    private static final int SHEMA = 1;
    static final String TABLE = "todos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,SHEMA);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todos (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_TEXT + " TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }
}
