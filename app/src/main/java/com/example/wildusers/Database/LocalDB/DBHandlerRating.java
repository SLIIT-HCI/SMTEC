package com.example.wildusers.Database.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandlerRating extends SQLiteOpenHelper {
    public DBHandlerRating(Context context) {
        super(context, "SMTEC_WILD.db", null, 1 );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create Table Ratings(name TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table if exists Ratings");
    }

    public Boolean insertData(String Name){
        SQLiteDatabase d = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", Name);
        long result =  d.insert("Ratings", null, contentValues);

        if(result == -1){
            return false;
        }
        else
            return true;
    }
}
