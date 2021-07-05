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

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 40;

    // Database Name
    private static final String DATABASE_NAME = "SmtecWildMobileApp6.db";
    private static final String TABLE1_NAME = "Phrase";
    private static final String COLUMN1_NAME = "ID";
    private static final String COLUMN2_NAME = "ActualPhrase";



    /********************** WILD USER details **************************/
    //User Details Storing
    private static final String WILD_USER_DETAIL_TABLE_NAME = "WildUser";
    private static final String COLUMN_NAME_ID = "User_ID";
    private static final String COLUMN_NAME_CONDITION = "Condition";
    private static final String COLUMN_NAME_RS = "Rotational_Sequence";
   /**************************************************************************/


    public static final String TABLE2_NAME = "WildExperiment";
    public static final String COLUMN1_id = "PassUserID";
    public static final String COLUMN2_session = "sessionNo";
    public static final String COLUMN2_attempt = "noOfRuns";
    public static final String COLUMN3_dateTime = "dateTime";
    public static final String COLUMN4_S1 = "stimulusPhrase";
    public static final String COLUMN5_S2 = "responsePhrase";
    public static final String COLUMN6_EditDistance = "edit_distance";
    public static final String COLUMN7_inputMethod = "inputMethod";
    public static final String COLUMN8_duration = "duration";

    private static final String TABLE3_NAME = "Rating";
    private static final String COLUMN2_userId = "ID";
    //private static final String COLUMN_userSession = "userSession";
    //private static final String COLUMN_dateTime = "FinishedTime";
    //private static final String COLUMN_userNoOfruns = "runs";
    private static final String COLUMN_speed = "Speed";
    private static final String COLUMN_accuracy = "Accuracy";
    private static final String COLUMN_preference = "preference";
    private static final String COLUMN_easeOfUse = "easeOfUse";
//    private static final String COLUMN_handPosture1 = "handPosture1";
//    private static final String COLUMN_commentR = "commentOfTest";

    private static final String TABLE4_NAME = "User";
    private static final String COLUMN1_userEmail = "Email";
    private static final String COLUMN2_Session = "ExperimentSession";

//    private static final String TABLE5_NAME = "Comment";
//    private static final String COLUMN_Email = "Email";
//    private static final String COLUMN_session = "session";
//    private static final String COLUMN_time = "FinishedTime";
//    private static final String COLUMN_handPosture = "HandPosture";
//    private static final String COLUMN_comment = "Comment";




//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
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
                    + COLUMN2_attempt + " INTEGER NOT NULL,"
                    + COLUMN3_dateTime + " TEXT NOT NULL,"
                    + COLUMN4_S1 + " TEXT NOT NULL,"
                    + COLUMN5_S2 + " TEXT NOT NULL,"
                    + COLUMN6_EditDistance + " INTEGER NOT NULL,"
                    + COLUMN7_inputMethod + " TEXT NOT NULL,"
                    + COLUMN8_duration + " REAL NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

            String TABLE_RATING = "CREATE TABLE " + TABLE3_NAME + "("
//                    + COLUMN2_userId + " TEXT PRIMARY KEY,"
//                    + COLUMN_userSession + " INTEGER NOT NULL,"
//                    + COLUMN_dateTime + " TEXT NOT NULL,"
//                    + COLUMN_userNoOfruns + " INTEGER NOT NULL,"
                    + COLUMN_speed + " INTEGER NOT NULL,"
                    + COLUMN_accuracy + " INTEGER NOT NULL,"
                    + COLUMN_preference + " INTEGER NOT NULL,"
                    + COLUMN_easeOfUse + " INTEGER NOT NULL )" ;
//                    + COLUMN_handPosture1 + " TEXT NOT NULL,"
//                    + COLUMN_commentR + " TEXT NOT NULL )";

            sqLiteDatabase.execSQL(TABLE_RATING);

            String TABLE_USER = "CREATE TABLE " + TABLE4_NAME + "("
                    + COLUMN1_userEmail + " TEXT PRIMARY KEY,"
                    + COLUMN2_Session + " INTEGER )";

            sqLiteDatabase.execSQL(TABLE_USER);

//            String TABLE_COMMENT = "CREATE TABLE " + TABLE5_NAME + "("
//                    + COLUMN_Email + " TEXT NOT NULL,"
//                    + COLUMN_session + " INTEGER NOT NULL,"
//                    + COLUMN_time + " TEXT NOT NULL,"
//                    + COLUMN_handPosture + " TEXT NOT NULL,"
//                    + COLUMN_comment + " TEXT NOT NULL )";
//
//            sqLiteDatabase.execSQL(TABLE_COMMENT);



            /****************** WILD USER DETAILS *************************************/
            String TABLE_WILDUSER = "CREATE TABLE " + WILD_USER_DETAIL_TABLE_NAME + "("
                    + COLUMN_NAME_ID + " TEXT NOT NULL,"
                    + COLUMN_NAME_CONDITION + " TEXT NOT NULL,"
                    + COLUMN_NAME_RS + " TEXT NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_WILDUSER);

            /**************************************************************************/



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
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE5_NAME);
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
//modify this during data storing of sample activity 1
//    public boolean insertData_Phrases2(String phrase)  {
//
//        Log.d("Trying to insert 1..","1");
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentvalues = new ContentValues();
//
//        contentvalues.put(COLUMN2_NAME, phrase);
//        long result = sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);
//        sqLiteDatabase.close();
//
//        if(result == -1)
//            return  false;
//        else
//            return  true;
//
//    }


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

//    public void saveToLocalDatabase(String email, int session, int attempt, Experiment ex, String inputMethod, SQLiteDatabase database){
//
//       ContentValues contentValues = new ContentValues();
//
//       contentValues.put(COLUMN1_email, email);
//       contentValues.put(COLUMN2_session, session);
//       contentValues.put(COLUMN2_attempt, attempt);
//       contentValues.put(COLUMN7_inputMethod, inputMethod);
//       contentValues.put(COLUMN3_dateTime, Arrays.toString(ex.getDurationTimeStamps()));
//       contentValues.put(COLUMN4_S1, ex.getStimulus());
//       contentValues.put(COLUMN5_S2, ex.getResponse());
//       contentValues.put(COLUMN6_EditDistance, ex.getEditDistance());
//       contentValues.put(COLUMN8_duration,ex.getDuration());
//
//       database.insert(TABLE2_NAME, null, contentValues);
//
//    }

    public void saveToLocalDatabase(String PassUserID, int session, int attempt, Experiment ex, SQLiteDatabase database){

        //SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN1_id, PassUserID);
        contentValues.put(COLUMN2_session, session);
        contentValues.put(COLUMN2_attempt, attempt);
        contentValues.put(COLUMN3_dateTime, Arrays.toString(ex.getDurationTimeStamps()));
        contentValues.put(COLUMN4_S1, ex.getStimulus());
        contentValues.put(COLUMN5_S2, ex.getResponse());
        contentValues.put(COLUMN6_EditDistance, ex.getEditDistance());
        contentValues.put(COLUMN8_duration,ex.getDuration());

        database.insert(TABLE2_NAME, null, contentValues);

}
//    public void saveToCommentTable(String email, int session,String dateTime, String handPosture, String comment, SQLiteDatabase database){
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COLUMN_Email, email);
//        contentValues.put(COLUMN_session, session);
//        contentValues.put(COLUMN_time, dateTime);
//        contentValues.put(COLUMN_handPosture, handPosture);
//        contentValues.put(COLUMN_comment, comment);
//
//        database.insert(TABLE5_NAME,null,contentValues);
//    }

//    public void saveToRatingsTable(String ID, float speed, float accuracy, float preference, float easeOfUse, String HandPosture, String comment, SQLiteDatabase database) {
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COLUMN2_userId, ID);
////        contentValues.put(COLUMN_userSession, session);
////        contentValues.put(COLUMN_dateTime, datetime);
////        contentValues.put(COLUMN_userNoOfruns,runs);
//        contentValues.put(COLUMN_speed, speed);
//        contentValues.put(COLUMN_accuracy, accuracy);
//        contentValues.put(COLUMN_preference, preference);
//        contentValues.put(COLUMN_easeOfUse, easeOfUse);
//        contentValues.put(COLUMN_handPosture1, HandPosture);
//        contentValues.put(COLUMN_commentR, comment);
//
//        database.insert(TABLE3_NAME,null,contentValues);
//    }

    public void saveToRatingsTable( float speed, float accuracy, float preference, float easeOfUse, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN2_userId, ID);
//        contentValues.put(COLUMN_userSession, session);
//        contentValues.put(COLUMN_dateTime, datetime);
//        contentValues.put(COLUMN_userNoOfruns,runs);
        contentValues.put(COLUMN_speed, speed);
        contentValues.put(COLUMN_accuracy, accuracy);
        contentValues.put(COLUMN_preference, preference);
        contentValues.put(COLUMN_easeOfUse, easeOfUse);

        database.insert(TABLE3_NAME,null,contentValues);
    }



    /**
     * @return*************************************************************************/
//    public void StoreUserDetails(String UID, String Condition, String RotationalSequence, SQLiteDatabase database){
//
//        //SQLiteDatabase database = new SQLiteDatabase();
//        //SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COLUMN_NAME_ID, UID);
//        contentValues.put(COLUMN_NAME_CONDITION, Condition);
//        contentValues.put(COLUMN_NAME_RS, RotationalSequence);
//
//        database.insert("WILD_USER_DETAIL_TABLE_NAME",null,contentValues);
//        //return true;
//    }

    public void StoreUserDetails(String UID, String Condition, String RotationalSequence){

        //SQLiteDatabase database = new SQLiteDatabase();
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_ID, UID);
        contentValues.put(COLUMN_NAME_CONDITION, Condition);
        contentValues.put(COLUMN_NAME_RS, RotationalSequence);

        database.insert(WILD_USER_DETAIL_TABLE_NAME,null,contentValues);
        //return true;
    }



    public Cursor readFromLocalDatabase(String UserEmail) {

        String query = ("SELECT * FROM " + TABLE2_NAME + " WHERE " + COLUMN1_id + "='" + UserEmail + "'");
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null){
           cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

//    public Cursor getCommentInfo(String UserEmail) {
//
//        String query = ("SELECT * FROM " + TABLE5_NAME + " WHERE " + COLUMN_Email + "='" + UserEmail + "'");
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if(database != null){
//            cursor = database.rawQuery(query, null);
//        }
//        return cursor;
//    }

    public Cursor getRatingInfo(String UserEmail) {

        String query = ("SELECT * FROM " + TABLE3_NAME + " WHERE " + COLUMN2_userId + "='" + UserEmail + "'");
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor getSessionValidationInfo() {

        String query = ("select email, sessionNo from LabExperiment");
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
}
