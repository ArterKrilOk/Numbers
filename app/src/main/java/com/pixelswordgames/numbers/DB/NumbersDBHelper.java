package com.pixelswordgames.numbers.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.pixelswordgames.numbers.DB.DBTables.SQL_CREATE_SCORES;
import static com.pixelswordgames.numbers.DB.DBTables.SQL_DELETE_SCORES;

public class NumbersDBHelper  extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "NumbersDB.db";

    public NumbersDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_SCORES);

        onCreate(db);
    }

}
