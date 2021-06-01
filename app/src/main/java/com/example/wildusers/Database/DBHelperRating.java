package com.example.wildusers.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperRating extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "smtecWild.db";
    public static final int DB_VERSION = 1;

    public DBHelperRating(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String RATING_TABLE = "CREATE TABLE " + RatingData.RatingDetails.TABLE_NAME+" ( " +
          RatingData.RatingDetails.COLUMN_ID + "INTEGER PRIMARY KEY,"+
          RatingData.RatingDetails.COLUMN_COMMENT + "TEXT)";

        DB.execSQL(RATING_TABLE);
    }


    /***************************** INSERTING RATING DATA ******************************************/
    public boolean addRatingData(int id, String comment){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues v = new ContentValues();

        v.put(RatingData.RatingDetails.COLUMN_ID, id);
        v.put(RatingData.RatingDetails.COLUMN_COMMENT, comment);

        long Rdata = db.insert(RatingData.RatingDetails.TABLE_NAME, null, v);

        if(Rdata > -1)
            return true;
        else
            return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
