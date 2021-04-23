package com.example.smtec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 45;

    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_FAILED = 1;
    public static final String SERVER_URL = Dbcontract.URL;

    // Database Name
    private static final String DATABASE_NAME = "smtecMobileApp.db";
    private static final String TABLE1_NAME = "PhraseSet";
    private static final String COLUMN1_NAME = "Phrase_ID";
    private static final String COLUMN2_NAME = "Phrase";

    public static final String TABLE2_NAME = "Experiment";
    public static final String COLUMN1_session = "session";
    public static final String COLUMN1_email = "email";
    public static final String COLUMN2_Duration = "duration";
    public static final String COLUMN3_S1 = "actualPhrase";
    public static final String COLUMN4_S2 = "inputPhrase";
    public static final String COLUMN5_EditDistance = "distance";
    public static final String COLUMN6_SYNC_STATUS = "Sync_Status";
    public static final String COLUMN7_SENSOR_NAME = "sensorName";
    public static final String COLUMN8_X = "x_value";
    public static final String COLUMN9_Y = "y_value";
    public static final String COLUMN10_Z = "Z_value";

   /* private static final String TABLE3_NAME = "Sensor";
    private static final String COLUMN1_ID = "sensorID";
    private static final String COLUMN2_SENSOR_NAME = "sensorName";
    private static final String COLUMN3_X = "x_value";
    private static final String COLUMN4_Y = "y_value";
    private static final String COLUMN5_Z = "Z_value";  */

    private static final String TABLE4_NAME = "User";
    private static final String COLUMN1_userEmail = "Email";
    private static final String COLUMN2_session = "Session";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       try{
            String TABLE_PHRASE = "CREATE TABLE " + TABLE1_NAME + "("
                    + COLUMN1_NAME + " INTEGER PRIMARY KEY,"
                    + COLUMN2_NAME + " TEXT )";

            sqLiteDatabase.execSQL(TABLE_PHRASE);

            String TABLE_EXPERIMENT = "CREATE TABLE " + TABLE2_NAME + "("
                    + COLUMN1_session + "INTEGER PRIMARY KEY ,"
                    + COLUMN1_email + " TEXT NOT NULL,"
                    + COLUMN2_Duration + " TEXT NOT NULL,"
                    + COLUMN3_S1 + " TEXT NOT NULL,"
                    + COLUMN4_S2 + " TEXT NOT NULL,"
                    + COLUMN5_EditDistance + " INTEGER NOT NULL,"
                    + COLUMN6_SYNC_STATUS  + " INTEGER NOT NULL,"
                    + COLUMN7_SENSOR_NAME  + " TEXT NOT NULL,"
                    + COLUMN8_X  + " REAL NOT NULL,"
                    + COLUMN9_Y  + " REAL NOT NULL,"
                    + COLUMN10_Z + "REAL NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

         /*   String TABLE_SENSOR = "CREATE TABLE " + TABLE3_NAME + "("
                    + COLUMN1_ID + " TEXT PRIMARY KEY ,"
                    + COLUMN2_SENSOR_NAME + " TEXT NOT NULL,"
                    + COLUMN3_X + " REAL NOT NULL,"
                    + COLUMN4_Y + " REAL NOT NULL,"
                    + COLUMN5_Z  + " REAL NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_SENSOR);    */

            String TABLE_USER = "CREATE TABLE " + TABLE4_NAME + "("
                    + COLUMN1_userEmail + " TEXT PRIMARY KEY,"
                    + COLUMN2_session + " INTEGER )";

            sqLiteDatabase.execSQL(TABLE_USER);

        } catch (Exception e) {
            Log.d("1", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);

        if(newVersion >oldVersion) {
           onCreate(sqLiteDatabase);
        }
    }
    public boolean insertData_Phrases(){

        Log.d("Trying to insert 1..","1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

            Log.d("Trying to insert 2..","1");
            contentvalues.put(COLUMN1_NAME, 1);
            contentvalues.put(COLUMN2_NAME, "Thanks, I will look at it tonight.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 2);
            contentvalues.put(COLUMN2_NAME, "She also wants us to hire everyone who is out on leave immediately instead of having them show up when released.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 3);
            contentvalues.put(COLUMN2_NAME, "I fear death is making a strong push down the backstretch.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 4);
            contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 5);
            contentvalues.put(COLUMN2_NAME, "This will be hard.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 6);
            contentvalues.put(COLUMN2_NAME, "Hopefully it cheered you up a bit.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 7);
            contentvalues.put(COLUMN2_NAME, "We are working on this as we speak.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 8);
            contentvalues.put(COLUMN2_NAME, "Interesting, are you around for a late lunch?");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 9);
            contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

            contentvalues.put(COLUMN1_NAME, 10);
            contentvalues.put(COLUMN2_NAME, "How about 9 in my office on 3825?");

            long result = sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
            sqLiteDatabase.close();

        if(result == -1)
            return  false;
        else
            return  true;
    }

     public ArrayList<String> getPhrases() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Phrase from PhraseSet",null);

        ArrayList<String> texts = new ArrayList<String>();
         if (cursor != null && cursor.moveToFirst()) {
             do {
                 // Passing values
                 String column = cursor.getString(0);
                 Log.d("column: "+ column,"1");
                 System.out.println("column my test: "+ column);
                 texts.add(column);

             } while(cursor.moveToNext());
         }

        cursor.close();
        db.close();

        return texts;
     }

   /* public boolean insertData_Experiment(String s1, String s2, int editDistance){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        try {
            contentvalues.put(COLUMN3_S1,s1);
            contentvalues.put(COLUMN4_S2,s2);
            contentvalues.put(COLUMN5_EditDistance,editDistance);
            sqLiteDatabase.insert(TABLE2_NAME, null, contentvalues);

        } catch (Exception e) {
            return false;
        }
        return true;
    }*/
	public boolean saveToLocalDatabase(String email,int session,String duration,String s1,String s2,int editDistance,String name,double val_x,double val_y,double val_z,int sync_status,SQLiteDatabase database){

       // SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN1_session,session);
            contentValues.put(COLUMN1_email,email);
            contentValues.put(COLUMN2_Duration,duration);
            contentValues.put(COLUMN3_S1,s1);
            contentValues.put(COLUMN4_S2,s2);
            contentValues.put(COLUMN5_EditDistance,editDistance);
            contentValues.put(COLUMN6_SYNC_STATUS,sync_status);
            contentValues.put(COLUMN7_SENSOR_NAME,name);
            contentValues.put(COLUMN8_X,val_x);
            contentValues.put(COLUMN9_Y,val_y);
            contentValues.put(COLUMN10_Z,val_z);

            database.insert(TABLE2_NAME, null, contentValues);

        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	public void updateUserStatus(String email, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN6_SYNC_STATUS, status);
        db.update(TABLE2_NAME, contentValues, COLUMN1_email + "=" + email, null);
        db.close();
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database) {

        String [] projection = {COLUMN1_session,COLUMN1_email,COLUMN2_Duration,COLUMN3_S1,COLUMN4_S2,COLUMN5_EditDistance,COLUMN6_SYNC_STATUS,COLUMN7_SENSOR_NAME,COLUMN8_X,COLUMN9_Y,COLUMN10_Z};
        return (database.query(TABLE2_NAME,projection,null,null,null,null,null));
    }
	
	/*  public boolean saveSensorDataToLocalDatabase(int sensorId,String name,double x_value,double y_value,double z_value,SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN1_ID,sensorId);
            contentValues.put(COLUMN2_SENSOR_NAME,name);
            contentValues.put(COLUMN3_X,x_value);
            contentValues.put(COLUMN4_Y,y_value);
            contentValues.put(COLUMN5_Z,z_value);

            database.insert(TABLE3_NAME, null, contentValues);

        } catch (Exception e) {
            return false;
        }
        return true;
    } */
}


