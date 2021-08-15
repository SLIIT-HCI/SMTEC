package com.example.machinelearningproject.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "Sensor.db";

    private static final String TABLE_SENSOR_STAND = "SensorStand";
    private static final String COLUMN_X1 = "x1";
    private static final String COLUMN_Y1 = "y1";
    private static final String COLUMN_Z1 = "z1";

    private static final String TABLE_SENSOR_WALK = "SensorWalk";
    private static final String COLUMN_X2 = "x2";
    private static final String COLUMN_Y2 = "y2";
    private static final String COLUMN_Z2 = "z2";

    private static final String TABLE_SENSOR_SLEEP = "SensorSleep";
    private static final String COLUMN_X3 = "x3";
    private static final String COLUMN_Y3 = "y3";
    private static final String COLUMN_Z3 = "z3";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String TABLE_STAND = "CREATE TABLE " + TABLE_SENSOR_STAND + "("
                + COLUMN_X1 + " TEXT,"
                + COLUMN_Y1 + " TEXT,"
                + COLUMN_Z1 + " TEXT )";

        sqLiteDatabase.execSQL(TABLE_STAND);

        String TABLE_WALK = "CREATE TABLE " + TABLE_SENSOR_WALK + "("
                + COLUMN_X2 + " TEXT,"
                + COLUMN_Y2 + " TEXT,"
                + COLUMN_Z2 + " TEXT )";

        sqLiteDatabase.execSQL(TABLE_WALK);

        String TABLE_RUN = "CREATE TABLE " + TABLE_SENSOR_SLEEP + "("
                + COLUMN_X3 + " TEXT,"
                + COLUMN_Y3 + " TEXT,"
                + COLUMN_Z3 + " TEXT )";

        sqLiteDatabase.execSQL(TABLE_RUN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_STAND);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_WALK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_SLEEP);

        if(newVersion >oldVersion) {
            onCreate(sqLiteDatabase);
        }
    }

    public void saveStandSensorDataToTable(String x1, String y1, String z1, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_X1, x1);
        contentValues.put(COLUMN_Y1, y1);
        contentValues.put(COLUMN_Z1, z1);
        database.insert(TABLE_SENSOR_STAND,null,contentValues);
    }

    public void saveWalkSensorDataToTable(String x2, String y2, String z2, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_X2, x2);
        contentValues.put(COLUMN_Y2, y2);
        contentValues.put(COLUMN_Z2, z2);
        database.insert(TABLE_SENSOR_WALK,null,contentValues);
    }

    public void saveSleepSensorDataToTable(String x3, String y3, String z3, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_X3, x3);
        contentValues.put(COLUMN_Y3, y3);
        contentValues.put(COLUMN_Z3, z3);
        database.insert(TABLE_SENSOR_SLEEP,null,contentValues);
    }
}
