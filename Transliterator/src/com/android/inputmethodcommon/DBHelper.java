package com.example.smtec_labuserexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SmtecLabMobileApp.db";
    private static final String TABLE1_NAME = "Phrase";
    private static final String COLUMN1_NAME = "ID";
    private static final String COLUMN2_NAME = "ActualPhrase";

    public static final String TABLE2_NAME = "LabExperiment";
    public static final String COLUMN1_userID = "user_id";
    public static final String COLUMN1_email = "email";
    public static final String COLUMN2_Duration = "duration";
    public static final String COLUMN3_S1 = "actualPhrase";
    public static final String COLUMN4_S2 = "inputPhrase";
    public static final String COLUMN5_EditDistance = "edit_distance";

    private static final String TABLE4_NAME = "User";
    private static final String COLUMN1_userEmail = "Email";
    private static final String COLUMN2_session = "Session";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       try{
            String TABLE_PHRASE = "CREATE TABLE " + TABLE1_NAME + "("
                    + COLUMN1_NAME + " INTEGER PRIMARY KEY,"
                    + COLUMN2_NAME + " TEXT )";

            sqLiteDatabase.execSQL(TABLE_PHRASE);

            String TABLE_EXPERIMENT = "CREATE TABLE " + TABLE2_NAME + "("
                    + COLUMN1_userID + "INTEGER PRIMARY KEY ,"
                    + COLUMN1_email + " TEXT NOT NULL,"
                    + COLUMN2_Duration + " TEXT NOT NULL,"
                    + COLUMN3_S1 + " TEXT NOT NULL,"
                    + COLUMN4_S2 + " TEXT NOT NULL,"
                    + COLUMN5_EditDistance + " INTEGER NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

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

    public boolean saveToLocalDatabase(int userId,String email,String duration,String s1,String s2,int editDistance,int id,int sync_status,SQLiteDatabase database){

       // SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN1_userID,userId);
            contentValues.put(COLUMN1_email,email);
            contentValues.put(COLUMN2_Duration,duration);
            contentValues.put(COLUMN3_S1,s1);
            contentValues.put(COLUMN4_S2,s2);
            contentValues.put(COLUMN5_EditDistance,editDistance);

            database.insert(TABLE2_NAME, null, contentValues);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database) {

        String [] projection = {COLUMN1_userID,COLUMN1_email,COLUMN2_Duration,COLUMN3_S1,COLUMN4_S2,COLUMN5_EditDistance};
        return (database.query(TABLE2_NAME,projection,null,null,null,null,null));
    }
}
