package com.devindicator.hivaids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bangujo on 16/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 9;
    public static final String DB_NAME = "angujomondi.db";
    public static final String TABLE_USER = "users";
    public static final String TABLE_PAGES = "pages";
    public static final String TABLE_NEWS = "news";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_ARTICLES = "articles";

    public static final String[] COLUMN_USER = {"id", "email", "age_in_range", "with_hiv", "kenyan_resident",
            "fear_disclosure", "fear_isolation", "fear_gossip", "prefer_hiv_vendor", "names",
            "fear_positive_result"};

    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (id INTEGER PRIMARY KEY, " +
            "email TEXT, age_in_range NUMERIC, with_hiv NUMERIC, kenyan_resident NUMERIC, " +
            "fear_disclosure NUMERIC, fear_isolation NUMERIC, fear_gossip NUMERIC, names TEXT, " +
            "prefer_hiv_vendor NUMERIC, fear_positive_result NUMERIC);";
    private static final String CREATE_PAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_PAGES + " (id INTEGER PRIMARY KEY, ptype TEXT, " +
            "title TEXT, content TEXT);";
    private static final String CREATE_NEWS = "CREATE TABLE IF NOT EXISTS " + TABLE_NEWS + " (id INTEGER PRIMARY KEY, source TEXT, " +
            "title TEXT, link TEXT, dated TEXT, thumbnail_link TEXT);";
    private static final String CREATE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (id INTEGER PRIMARY KEY, title TEXT, " +
            "description TEXT, position INTEGER);";
    private static final String CREATE_ARTICLES = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLES + " (id INTEGER PRIMARY KEY, title TEXT, " +
            "summary TEXT, category INTEGER, content TEXT, dated TEXT, thumbnail TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_PAGE);
        sqLiteDatabase.execSQL(CREATE_NEWS);
        sqLiteDatabase.execSQL(CREATE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PAGES);
        sqLiteDatabase.execSQL(CREATE_PAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        sqLiteDatabase.execSQL(CREATE_NEWS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_CATEGORIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        sqLiteDatabase.execSQL(CREATE_ARTICLES);
    }
}
