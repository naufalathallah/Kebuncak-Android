package com.example.kebuncak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama Tabel
    public static final String TABLE_SAYUR = "tbl_sayur";
    public static final String TABLE_USER = "tbl_user";

    // User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";

    // Nama kolom dalam tabel sayur
    public static final String _ID = "_id";
    public static final String SAYUR = "sayur";
    public static final String HARGA = "harga";

    // Nama Database
    static final String DB_NAME = "kebuncak.db";

    // Versi Database
    static final int DB_VERSION = 1;

    // create user table sql query
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // Membuat query tabel sayur
    private static final String CREATE_TABLE_SAYUR = "create table " + TABLE_SAYUR + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SAYUR + " TEXT NOT NULL, " + HARGA + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SAYUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAYUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
