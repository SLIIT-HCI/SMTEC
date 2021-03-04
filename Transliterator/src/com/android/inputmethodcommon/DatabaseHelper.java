package com.example.smtec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "smtec.db";
    private static final String TABLE1_NAME = "PhraseSet";
    private static final String COLUMN1_NAME = "Phrase_ID";
    private static final String COLUMN2_NAME = "Phrase";

    private static final String TABLE2_NAME = "Experiment";
    private static final String COLUMN1_email = "email";
    private static final String COLUMN2_ID = "sensorID";
    private static final String COLUMN3_session = "session";
    private static final String COLUMN4_S1 = "actualPhrase";
    private static final String COLUMN5_S2 = "inputPhrase";
    private static final String COLUMN6_Duration = "duration";
    private static final String COLUMN7_EditDistance = "distance";
  //  private static final String COLUMN9_SensorReading = "sensor";

    private static final String TABLE3_NAME = "Sensor";
    private static final String COLUMN1_ID = "sensorID";
    private static final String COLUMN2_SENSOR_NAME = "sensorName";
    private static final String COLUMN3_X = "x_value";
    private static final String COLUMN4_Y = "y_value";
    private static final String COLUMN5_Z = "Z_value";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            String TABLE_PHRASE = "CREATE TABLE " + TABLE1_NAME + "("
                    + COLUMN1_NAME + " INTEGER PRIMARY KEY,"
                    + COLUMN2_NAME + " TEXT )";

            sqLiteDatabase.execSQL(TABLE_PHRASE);

            String TABLE_EXPERIMENT = "CREATE TABLE " + TABLE2_NAME + "("
                    + COLUMN1_email + " TEXT PRIMARY KEY ,"
                    + COLUMN2_ID + " INTEGER NOT NULL,"
                    + COLUMN3_session + " INTEGER NOT NULL,"
                    + COLUMN4_S1 + " TEXT NOT NULL,"
                    + COLUMN5_S2 + " TEXT NOT NULL,"
                    + COLUMN6_Duration + " TEXT NOT NULL,"
                    + COLUMN7_EditDistance  + " INTEGER NOT NULL," +
                    "CONSTRAINT fk_TABLE3_NAME" +
                    "    FOREIGN KEY (COLUMN1_ID)" +
                    "    REFERENCES TABLE3_NAME(COLUMN1_ID))";

            sqLiteDatabase.execSQL(TABLE_EXPERIMENT);

            String TABLE_SENSOR = "CREATE TABLE " + TABLE3_NAME + "("
                    + COLUMN1_ID + " TEXT PRIMARY KEY ,"
                    + COLUMN2_SENSOR_NAME + " TEXT NOT NULL,"
                    + COLUMN3_X + " REAL NOT NULL,"
                    + COLUMN4_Y + " REAL NOT NULL,"
                    + COLUMN5_Z  + " REAL NOT NULL)";

            sqLiteDatabase.execSQL(TABLE_SENSOR);

        } catch (Exception e) {
            Log.d("1", e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        if(newVersion >oldVersion) {

            onCreate(sqLiteDatabase);
        }
    }
    public boolean insertData_Phrases(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        try {
            contentvalues.put(COLUMN1_NAME, 1);
            contentvalues.put(COLUMN1_NAME, 2);
            contentvalues.put(COLUMN1_NAME, 3);
            contentvalues.put(COLUMN1_NAME, 4);
            contentvalues.put(COLUMN1_NAME, 5);
            contentvalues.put(COLUMN1_NAME, 6);
            contentvalues.put(COLUMN1_NAME, 7);
            contentvalues.put(COLUMN1_NAME, 8);
            contentvalues.put(COLUMN1_NAME, 9);
            contentvalues.put(COLUMN1_NAME, 10);
            contentvalues.put(COLUMN1_NAME, 11);
            contentvalues.put(COLUMN1_NAME, 12);
            contentvalues.put(COLUMN1_NAME, 13);
            contentvalues.put(COLUMN1_NAME, 14);
            contentvalues.put(COLUMN1_NAME, 15);
            contentvalues.put(COLUMN1_NAME, 16);
            contentvalues.put(COLUMN1_NAME, 17);
            contentvalues.put(COLUMN1_NAME, 18);
            contentvalues.put(COLUMN1_NAME, 19);
            contentvalues.put(COLUMN1_NAME, 20);
            contentvalues.put(COLUMN1_NAME, 21);
            contentvalues.put(COLUMN1_NAME, 22);
            contentvalues.put(COLUMN1_NAME, 23);
            contentvalues.put(COLUMN1_NAME, 24);
            contentvalues.put(COLUMN1_NAME, 25);
            contentvalues.put(COLUMN1_NAME, 26);
            contentvalues.put(COLUMN1_NAME, 27);
            contentvalues.put(COLUMN1_NAME, 28);
            contentvalues.put(COLUMN1_NAME, 29);
            contentvalues.put(COLUMN1_NAME, 30);

            contentvalues.put(COLUMN2_NAME, "Thanks, I will look at it tonight.");
            contentvalues.put(COLUMN2_NAME, "Thanks, I will look at it tonight.");
            contentvalues.put(COLUMN2_NAME, "She also wants us to hire everyone who is out on leave immediately instead of having them show up when released.");
            contentvalues.put(COLUMN2_NAME, "I fear death is making a strong push down the backstretch.");
            contentvalues.put(COLUMN2_NAME, "I've got a call into Wes to see if these numbers were in the second current estimate or not.");
            contentvalues.put(COLUMN2_NAME, "This will be hard.");
            contentvalues.put(COLUMN2_NAME, "Hopefully it cheered you up a bit.");
            contentvalues.put(COLUMN2_NAME, "We are working on this as we speak.");
            contentvalues.put(COLUMN2_NAME, "Interesting, are you around for a late lunch?");
            contentvalues.put(COLUMN2_NAME, "How about 9 in my office on 3825?");

            contentvalues.put(COLUMN2_NAME, "Have a good evening.");
            contentvalues.put(COLUMN2_NAME, "Sounds good to me.");
            contentvalues.put(COLUMN2_NAME, "Yes I am here actually.");
            contentvalues.put(COLUMN2_NAME, "Tonight is my anniversary.");
            contentvalues.put(COLUMN2_NAME, "Thanks, I think we have taken care of this.");
            contentvalues.put(COLUMN2_NAME, "Will follow up today.");
            contentvalues.put(COLUMN2_NAME, "That's a lot of dollars.");
            contentvalues.put(COLUMN2_NAME, "Are you going to join us for lunch?");
            contentvalues.put(COLUMN2_NAME, "Thanks for the surprise.");
            contentvalues.put(COLUMN2_NAME, "I will have to check to see who handles the paralegal hiring..");

            contentvalues.put(COLUMN2_NAME, "I can't find it on my Blackberry.");
            contentvalues.put(COLUMN2_NAME, "And how would I be going for work?");
            contentvalues.put(COLUMN2_NAME, "I hoped you did.");
            contentvalues.put(COLUMN2_NAME, "Early to my appointment, so I'm working some.");
            contentvalues.put(COLUMN2_NAME, "OK to make changes, change out original.");
            contentvalues.put(COLUMN2_NAME, "That would probably be the easiest time to give them up.");
            contentvalues.put(COLUMN2_NAME, "I heard it was at 5?");
            contentvalues.put(COLUMN2_NAME, "I have no idea who told John this as I haven't had a discussion on this issue since 1999.");
            contentvalues.put(COLUMN2_NAME, "We'll let you know.");
            contentvalues.put(COLUMN2_NAME, "Do you want to eat lunch somewhere before?");

            sqLiteDatabase.insert(TABLE1_NAME, null, contentvalues);

        } catch (Exception e) {
            return false;
        }
          return true;
    }

    public ArrayList getPhrases(){

        ArrayList<String> phrases = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE1_NAME,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()) {
           phrases.add(cursor.getString(cursor.getColumnIndex(COLUMN2_NAME)));
           cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return phrases;
    }

    public boolean insertData_Experiment(String phrase){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        try {
            contentvalues.put(COLUMN5_S2, phrase);
            sqLiteDatabase.insert(TABLE2_NAME, null, contentvalues);

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
