package com.example.labexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 16;

    // Database Name
    private static final String DATABASE_NAME = "SmtecLabMobileApp.db";
    private static final String TABLE1_NAME = "Phrase";
    private static final String COLUMN1_NAME = "ID";
    private static final String COLUMN2_NAME = "ActualPhrase";

    public static final String TABLE2_NAME = "LabExperiment";
    public static final String COLUMN1_ID = "UserID";
    public static final String COLUMN1_email = "email";
    public static final String COLUMN2_session = "sessionNo";
    public static final String COLUMN3_dateTime = "dateTime";
    public static final String COLUMN4_S1 = "actualPhrase";
    public static final String COLUMN5_S2 = "inputPhrase";
    public static final String COLUMN6_EditDistance = "edit_distance";

    private static final String TABLE4_NAME = "User";
    private static final String COLUMN1_userEmail = "Email";
    private static final String COLUMN2_Session = "ExperimentSession";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            String TABLE_PHRASE = "CREATE TABLE " + TABLE1_NAME + "("
                    + COLUMN1_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN2_NAME + " TEXT )";

            sqLiteDatabase.execSQL(TABLE_PHRASE);

            String TABLE_EXPERIMENT = "CREATE TABLE " + TABLE2_NAME + "("
                    + COLUMN1_email + " TEXT NOT NULL,"
                    + COLUMN1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN2_session + " INTEGER NOT NULL,"
                    + COLUMN3_dateTime + " TEXT NOT NULL,"
                    + COLUMN4_S1 + " TEXT NOT NULL,"
                    + COLUMN5_S2 + " TEXT NOT NULL,"
                    + COLUMN6_EditDistance + " INTEGER NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

            String TABLE_USER = "CREATE TABLE " + TABLE4_NAME + "("
                    + COLUMN1_userEmail + " TEXT PRIMARY KEY,"
                    + COLUMN2_Session + " INTEGER )";

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

        contentvalues.put(COLUMN2_NAME, "Thanks, I will look at it tonight.");
        contentvalues.put(COLUMN2_NAME, "She also wants us to hire everyone who is out on leave immediately instead of having them show up when released.");
        contentvalues.put(COLUMN2_NAME, "I fear death is making a strong push down the backstretch.");
        contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");
        contentvalues.put(COLUMN2_NAME, "This will be hard.");
        contentvalues.put(COLUMN2_NAME, "Hopefully it cheered you up a bit.");
        contentvalues.put(COLUMN2_NAME, "We are working on this as we speak.");
        contentvalues.put(COLUMN2_NAME, "Interesting, are you around for a late lunch?");
        contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");
        contentvalues.put(COLUMN2_NAME, "How about 9 in my office on 3825?");


        contentvalues.put(COLUMN2_NAME, "Can you rough out a slide on rating agencies?");
        contentvalues.put(COLUMN2_NAME, "We probably have to discuss trade behavior and margin.");
        contentvalues.put(COLUMN2_NAME, "Tell her to get my expense report done.");
        contentvalues.put(COLUMN2_NAME, "I didn't understand we were borrowing them.");
        contentvalues.put(COLUMN2_NAME, "Let me know if I miss anything.");
        contentvalues.put(COLUMN2_NAME, "Did you talk to Ava this morning?");
        contentvalues.put(COLUMN2_NAME, "I'm at the doctor's office, should be in later.");
        contentvalues.put(COLUMN2_NAME, "I am not concerned with the Brown money.");
        contentvalues.put(COLUMN2_NAME, "See you in Enron House when I get back.");
        contentvalues.put(COLUMN2_NAME, "Don't know how that happened.");


        contentvalues.put(COLUMN2_NAME, "I wanted to go drinking with you.");
        contentvalues.put(COLUMN2_NAME, "Neil has been asking around.");
        contentvalues.put(COLUMN2_NAME, "I have rates and capacity utilization information ready.");
        contentvalues.put(COLUMN2_NAME, "Got your back while you pick up wine.");
        contentvalues.put(COLUMN2_NAME, "We haven't made that decision here though.");
        contentvalues.put(COLUMN2_NAME, "We haven't made that decision here though.");
        contentvalues.put(COLUMN2_NAME, "I have forwarded to Kelly.");
        contentvalues.put(COLUMN2_NAME, "I am really not wanting to come back.");
        contentvalues.put(COLUMN2_NAME, "I spoke with Lara Robinson this morning.");
        contentvalues.put(COLUMN2_NAME, "Please stay on top of the security issue.");

        long result = sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        sqLiteDatabase.close();

        if(result == -1)
            return  false;
        else
            return  true;
    }

    public ArrayList<String> getPhrases() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select ActualPhrase from Phrase",null);

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

    public void saveToLocalDatabase(String email, int session,ArrayList<Experiment> ex,SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN1_email, email);
            contentValues.put(COLUMN2_session, session);
            for(int i=0;i<ex.size();i++){
              contentValues.put(COLUMN3_dateTime, Arrays.toString(ex.get(i).getDurationTimeStamps()));
              contentValues.put(COLUMN4_S1, ex.get(i).getStimulus());
              contentValues.put(COLUMN5_S2, ex.get(i).getResponse());
              contentValues.put(COLUMN6_EditDistance, ex.get(i).getEditDistance());
            }
        database.insert(TABLE2_NAME, null, contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database) {

        String [] projection = {COLUMN1_email,COLUMN2_session,COLUMN3_dateTime,COLUMN4_S1,COLUMN5_S2,COLUMN6_EditDistance};
        return (database.query(TABLE2_NAME,projection,null,null,null,null,null));
    }

}
