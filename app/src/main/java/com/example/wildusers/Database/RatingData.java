package com.example.wildusers.Database;

import android.provider.BaseColumns;

public class RatingData {
    public RatingData(){}

    public static final class RatingDetails implements BaseColumns{
        private static final String DATABASE_NAME = "smtecMobileApp.db";
        public static final String TABLE_NAME = "rating";
        public static final String COLUMN_ID = "rating_id";
        public static final String COLUMN_COMMENT = "Comments";

    }
}
