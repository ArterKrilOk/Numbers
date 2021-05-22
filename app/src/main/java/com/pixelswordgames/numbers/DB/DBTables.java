package com.pixelswordgames.numbers.DB;

import android.provider.BaseColumns;

public class DBTables {
    public static final String SQL_CREATE_SCORES = "CREATE TABLE " + ScoresTable.TABLE_NAME + " (" +
            ScoresTable.COL_ID + " INTEGER PRIMARY KEY," +
            ScoresTable.COL_LVL + " INTEGER," +
            ScoresTable.COL_TIME + " DOUBLE," +
            ScoresTable.COL_TIMESTAMP + " DATETIME default current_timestamp)";
    public final static String SQL_DELETE_SCORES = "DROP TABLE IF EXISTS " + ScoresTable.TABLE_NAME;

    public static class ScoresTable implements BaseColumns {
        public static final String TABLE_NAME = "Scores";

        public static final String COL_ID = "id";
        public static final String COL_LVL = "lvl";
        public static final String COL_TIME = "t";
        public static final String COL_TIMESTAMP = "d";
    }
}
