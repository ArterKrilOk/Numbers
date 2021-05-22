package com.pixelswordgames.numbers.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.pixelswordgames.numbers.Game.Score;

import java.util.ArrayList;
import java.util.List;

public class DBLab {

    private final SQLiteDatabase database;

    public static DBLab get(Context context){
        return  new DBLab(context);
    }

    private DBLab(Context context){
        database = new NumbersDBHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public int getLastLvl(){
        ScoreCursorWrapper cursor = queryFavorite(null,null, DBTables.ScoresTable.COL_LVL);
        int lvl = 1;
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if (cursor.getScore().level > lvl)
                    lvl = cursor.getScore().level;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return lvl;
    }


    public double getHighScore(int lvl){
        ScoreCursorWrapper cursor = queryFavorite(DBTables.ScoresTable.COL_LVL + " = " + lvl,null, DBTables.ScoresTable.COL_TIME);
        double bestTime = Double.MAX_VALUE;
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if (bestTime > cursor.getScore().time)
                    bestTime = cursor.getScore().time;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        if(bestTime == Double.MAX_VALUE)
            return 0;
        return bestTime;
    }

    public int getGamesCount(int lvl){
        ScoreCursorWrapper cursor = queryFavorite(DBTables.ScoresTable.COL_LVL + " = " + lvl,null, null);
        int count = 0;
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                count++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return count;
    }

    public void saveScore(Score score){
        ContentValues values = getScoreContentValues(score);
        database.insert(DBTables.ScoresTable.TABLE_NAME,null, values);
    }

    private static ContentValues getScoreContentValues(Score score){
        ContentValues values = new ContentValues();

        values.put(DBTables.ScoresTable.COL_LVL, score.level);
        values.put(DBTables.ScoresTable.COL_TIME, score.time);

        return values;
    }

    private ScoreCursorWrapper queryFavorite(String whereClause, String[] whereArgs, String orderBy){
        Cursor cursor = database.query(
                DBTables.ScoresTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );
        return new ScoreCursorWrapper(cursor);
    }

    private class ScoreCursorWrapper extends CursorWrapper {
        public ScoreCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Score getScore(){
            return new Score(
                getInt(getColumnIndex(DBTables.ScoresTable.COL_LVL)),
                getDouble(getColumnIndex(DBTables.ScoresTable.COL_TIME)),
                getString(getColumnIndex(DBTables.ScoresTable.COL_TIMESTAMP))
            );
        }
    }
}
