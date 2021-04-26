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

    private static final int DATABASE_VERSION = 24;

    // Database Name
    private static final String DATABASE_NAME = "SmtecLabMobileApp.db";
    private static final String TABLE1_NAME = "Phrase";
    private static final String COLUMN1_NAME = "ID";
    private static final String COLUMN2_NAME = "ActualPhrase";

    public static final String TABLE2_NAME = "LabExperiment";
    public static final String COLUMN1_email = "email";
    public static final String COLUMN2_session = "sessionNo";
    public static final String COLUMN3_dateTime = "dateTime";
    public static final String COLUMN4_S1 = "actualPhrase";
    public static final String COLUMN5_S2 = "inputPhrase";
    public static final String COLUMN6_EditDistance = "edit_distance";
    public static final String COLUMN7_inputMethod = "inputMethod";

    private static final String TABLE3_NAME = "Rating";
    private static final String COLUMN_userEmail = "userEmail";
    private static final String COLUMN_userSession = "userSession";
    private static final String COLUMN_speed = "Speed";
    private static final String COLUMN_accuracy = "Accuracy";
    private static final String COLUMN_preference = "preference";
    private static final String COLUMN_easeOfUse = "easeOfUse";

    private static final String TABLE4_NAME = "User";
    private static final String COLUMN1_userEmail = "Email";
    private static final String COLUMN2_Session = "ExperimentSession";

    private static final String TABLE5_NAME = "Comment";
    private static final String COLUMN_handPosture = "HandPosture";
    private static final String COLUMN_comment = "Comment";

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
                    + COLUMN2_session + " INTEGER NOT NULL,"
                    + COLUMN3_dateTime + " TEXT NOT NULL,"
                    + COLUMN4_S1 + " TEXT NOT NULL,"
                    + COLUMN5_S2 + " TEXT NOT NULL,"
                    + COLUMN6_EditDistance + " INTEGER NOT NULL,"
                    + COLUMN7_inputMethod + " TEXT NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

            String TABLE_RATING = "CREATE TABLE " + TABLE3_NAME + "("
                    + COLUMN_userEmail + " TEXT PRIMARY KEY,"
                    + COLUMN_userSession + " INTEGER NOT NULL,"
                    + COLUMN_speed + " INTEGER NOT NULL,"
                    + COLUMN_accuracy + " INTEGER NOT NULL,"
                    + COLUMN_preference + " INTEGER NOT NULL,"
                    + COLUMN_easeOfUse + " INTEGER NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_RATING);

            String TABLE_USER = "CREATE TABLE " + TABLE4_NAME + "("
                    + COLUMN1_userEmail + " TEXT PRIMARY KEY,"
                    + COLUMN2_Session + " INTEGER )";

            sqLiteDatabase.execSQL(TABLE_USER);

            String TABLE_COMMENT = "CREATE TABLE " + TABLE5_NAME + "("
                    + COLUMN_handPosture + " TEXT NOT NULL,"
                    + COLUMN_comment + " TEXT NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_COMMENT);

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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE5_NAME);

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
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "She also wants us to hire everyone who is out on leave immediately instead of having them show up when released.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I fear death is making a strong push down the backstretch.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "This will be hard.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Hopefully it cheered you up a bit.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "We are working on this as we speak.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Interesting, are you around for a late lunch?");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "How about 9 in my office on 3825?");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

        contentvalues.put(COLUMN2_NAME, "Can you rough out a slide on rating agencies?");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "We probably have to discuss trade behavior and margin.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Tell her to get my expense report done.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I didn't understand we were borrowing them.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Let me know if I miss anything.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Did you talk to Ava this morning?");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I'm at the doctor's office, should be in later.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I am not concerned with the Brown money.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "See you in Enron House when I get back.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Don't know how that happened.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

        contentvalues.put(COLUMN2_NAME, "I wanted to go drinking with you.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Neil has been asking around.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I have rates and capacity utilization information ready.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "Got your back while you pick up wine.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "We haven't made that decision here though.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "We haven't made that decision here though.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I have forwarded to Kelly.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I am really not wanting to come back.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
        contentvalues.put(COLUMN2_NAME, "I spoke with Lara Robinson this morning.");
        sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
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

    public void saveToLocalDatabase(String email, int session, Experiment ex, String inputMethod, SQLiteDatabase database){

       ContentValues contentValues = new ContentValues();

       contentValues.put(COLUMN1_email, email);
       contentValues.put(COLUMN2_session, session);
       contentValues.put(COLUMN7_inputMethod, inputMethod);
       contentValues.put(COLUMN3_dateTime, Arrays.toString(ex.getDurationTimeStamps()));
       contentValues.put(COLUMN4_S1, ex.getStimulus());
       contentValues.put(COLUMN5_S2, ex.getResponse());
       contentValues.put(COLUMN6_EditDistance, ex.getEditDistance());

       database.insert(TABLE2_NAME, null, contentValues);

    }

    public void saveToCommentTable(String handPosture, String comment,SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_handPosture, handPosture);
        contentValues.put(COLUMN_comment, comment);

        database.insert(TABLE5_NAME,null,contentValues);
    }

    public void saveToRatingsTable(String email, int session, float speed, float accuracy, float preference, float easeOfUse, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_userEmail, email);
        contentValues.put(COLUMN_userSession, session);
        contentValues.put(COLUMN_speed, speed);
        contentValues.put(COLUMN_accuracy, accuracy);
        contentValues.put(COLUMN_preference, preference);
        contentValues.put(COLUMN_easeOfUse, easeOfUse);

        database.insert(TABLE3_NAME,null,contentValues);
    }

    public Cursor readFromLocalDatabase() {

       // String [] projection = {COLUMN1_email,COLUMN2_session,COLUMN3_dateTime,COLUMN4_S1,COLUMN5_S2,COLUMN6_EditDistance};
      //  return (database.query(TABLE2_NAME,projection,null,null,null,null,null));

        String query = "SELECT * FROM " + TABLE2_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null){
           cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
}
