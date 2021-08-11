package com.example.wildusers.Database.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wildusers.Experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 76;

    // Database Name
    private static final String DATABASE_NAME = "SmtecWildMobileApp6.db";
    private static final String TABLE1_NAME = "Phrase";
    private static final String COLUMN1_NAME = "ID";
    private static final String COLUMN2_NAME = "ActualPhrase";

    //Storing User Details
    private static final String WILD_USER_DETAIL_TABLE_NAME = "WildUser";
    private static final String COLUMN_NAME_ID = "User_ID";
    private static final String COLUMN_NAME_CONDITION = "Condition";
    private static final String COLUMN_NAME_RS = "Rotational_Sequence";

    //Storing Wild Experiment Details
    public static final String TABLE2_NAME = "WildExperiment";
    public static final String COLUMN1_id = "UserID";
    public static final String COLUMN2_session = "session";
    public static final String COLUMN3_dateTime = "dateTime";
    public static final String COLUMN4_S1 = "stimulusPhrase";
    public static final String COLUMN5_S2 = "responsePhrase";
    public static final String COLUMN6_EditDistance = "edit_distance";
    public static final String COLUMN7_sample = "sample";
    public static final String COLUMN8_duration = "duration";

    //Storing Rating Details
    private static final String TABLE3_NAME = "Rating";
    private static final String COLUMN2_userId = "UID";
    private static final String COLUMN_userSession = "userSession";
    private static final String COLUMN_speed = "Speed";
    private static final String COLUMN_accuracy = "Accuracy";
    private static final String COLUMN_preference = "preference";
    private static final String COLUMN_easeOfUse = "easeOfUse";
    private static final String COLUMN_handPosture1 = "handPosture1";
    private static final String COLUMN_commentR = "commentOfTest";

    //Storing Questionnaire Details
    private static final String TABLE_QUESTIONNAIRE = "Questionnaire";
    private static final String COLUMNQ_userid = "ID";
    private static final String COLUMN_move = "Move";
    private static final String COLUMN_walk = "Walk";
    private static final String COLUMN_busy = "Busy";
    private static final String COLUMN_tired = "Tired";

    //Storing Active Time Details
    private static final String TABLE_ACTIVE_TIME = "ActiveTimeSelection";
    private static final String COLUMNAT_userid = "U_ID";
    private static final String COLUMN_STARTTIME = "StartTime";
    private static final String COLUMN_ENDTIME = "EndTime";


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
                    + COLUMN1_id + " TEXT NOT NULL,"
                    + COLUMN2_session + " INTEGER NOT NULL,"
                    //+ COLUMN2_attempt + " INTEGER NOT NULL,"
                    + COLUMN3_dateTime + " TEXT NOT NULL,"
                    + COLUMN4_S1 + " TEXT NOT NULL,"
                    + COLUMN5_S2 + " TEXT NOT NULL,"
                    + COLUMN7_sample + " INTEGER NOT NULL,"
                    + COLUMN6_EditDistance + " INTEGER NOT NULL,"
                    + COLUMN8_duration + " REAL NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

            String TABLE_RATING = "CREATE TABLE " + TABLE3_NAME + "("
                    + COLUMN2_userId + " TEXT,"
                    + COLUMN_userSession + " INTEGER NOT NULL,"
                    + COLUMN_speed + " INTEGER NOT NULL,"
                    + COLUMN_accuracy + " INTEGER NOT NULL,"
                    + COLUMN_preference + " TEXT NOT NULL,"
                    + COLUMN_easeOfUse + " INTEGER NOT NULL,"
                    + COLUMN_handPosture1 + " TEXT NOT NULL,"
                    + COLUMN_commentR + " TEXT NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_RATING);


            String TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTIONNAIRE + "("
                    + COLUMNQ_userid + " TEXT,"
                    + COLUMN_move + " INTEGER NOT NULL,"
                    + COLUMN_walk + " INTEGER NOT NULL,"
                    + COLUMN_busy + " INTEGER NOT NULL,"
                    + COLUMN_tired + " INTEGER NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_QUESTION);

            String TABLE_ACTIVE_TIME_SELECTOR = "CREATE TABLE " + TABLE_ACTIVE_TIME + "("
                    + COLUMNAT_userid + " TEXT,"
                    + COLUMN_STARTTIME + " TEXT,"
                    + COLUMN_ENDTIME + " TEXT )";

            sqLiteDatabase.execSQL(TABLE_ACTIVE_TIME_SELECTOR);


            String TABLE_WILDUSER = "CREATE TABLE " + WILD_USER_DETAIL_TABLE_NAME + "("
                    + COLUMN_NAME_ID + " TEXT NOT NULL,"
                    + COLUMN_NAME_CONDITION + " TEXT NOT NULL,"
                    + COLUMN_NAME_RS + " TEXT NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_WILDUSER);

        } catch (Exception e) {
            Log.d("1", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONNAIRE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE_TIME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WILD_USER_DETAIL_TABLE_NAME);

        if(newVersion >oldVersion) {
            onCreate(sqLiteDatabase);
        }
    }
    
    public boolean insertData_Phrases(String phrase)  {

        Log.d("Trying to insert 1..","1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(COLUMN2_NAME, phrase);
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


    public void saveToLocalDatabase(String UserID, int sample, int session, Experiment ex, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN1_id, UserID);
        contentValues.put(COLUMN7_sample, sample);
        contentValues.put(COLUMN2_session, session);
        contentValues.put(COLUMN3_dateTime, Arrays.toString(ex.getDurationTimeStamps()));
        contentValues.put(COLUMN4_S1, ex.getStimulus());
        contentValues.put(COLUMN5_S2, ex.getResponse());
        contentValues.put(COLUMN6_EditDistance, ex.getEditDistance());
        contentValues.put(COLUMN8_duration,ex.getDuration());

        database.insert(TABLE2_NAME, null, contentValues);

}

    public void saveToRatingsTable(String ID, int session, float speed, float accuracy, String preference, float easeOfUse,  String HandPosture, String comment,  SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN2_userId, ID);
        contentValues.put(COLUMN_userSession, session);
        contentValues.put(COLUMN_speed, speed);
        contentValues.put(COLUMN_accuracy, accuracy);
        contentValues.put(COLUMN_preference, preference);
        contentValues.put(COLUMN_easeOfUse, easeOfUse);
        contentValues.put(COLUMN_handPosture1, HandPosture);
        contentValues.put(COLUMN_commentR, comment);


        database.insert(TABLE3_NAME,null,contentValues);
    }

    public void saveToQuestionnaireTable(String ID, float move, float walk, float busy, float tired, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMNQ_userid, ID);
        contentValues.put(COLUMN_move, move);
        contentValues.put(COLUMN_walk, walk);
        contentValues.put(COLUMN_busy, busy);
        contentValues.put(COLUMN_tired, tired);

        database.insert(TABLE_QUESTIONNAIRE,null,contentValues);
    }


    public void StoreUserDetails(String UID, String Condition, String RotationalSequence){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_ID, UID);
        contentValues.put(COLUMN_NAME_CONDITION, Condition);
        contentValues.put(COLUMN_NAME_RS, RotationalSequence);

        database.insert(WILD_USER_DETAIL_TABLE_NAME,null,contentValues);
    }

    public void StoreActiveTimeSelector(String UID, String start, String end, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMNAT_userid, UID);
        contentValues.put(COLUMN_STARTTIME, start);
        contentValues.put(COLUMN_ENDTIME, end);

        database.insert(TABLE_ACTIVE_TIME,null,contentValues);
    }
}
