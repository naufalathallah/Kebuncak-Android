package com.example.kebuncak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }
    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Query add user
    public void addUser(String uname, String pw) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.COLUMN_USER_NAME, uname);
        contentValue.put(DatabaseHelper.COLUMN_USER_PASSWORD, pw);
        database.insert(DatabaseHelper.TABLE_USER, null, contentValue);
    }

    // Query insert data
    public void insert(String sayur, String harga) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SAYUR, sayur);
        contentValue.put(DatabaseHelper.HARGA, harga);
        database.insert(DatabaseHelper.TABLE_SAYUR, null, contentValue);
    }

    // Query ambil/read data
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SAYUR, DatabaseHelper.HARGA };
        Cursor cursor = database.query(DatabaseHelper.TABLE_SAYUR, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Query update data
    public int update(long _id, String sayur, String harga) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SAYUR, sayur);
        contentValues.put(DatabaseHelper.HARGA, harga);
        int i = database.update(DatabaseHelper.TABLE_SAYUR, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    // Query delete data
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_SAYUR, DatabaseHelper._ID + "=" + _id, null);
    }

    // user verify
    public boolean checkUser(String uname, String password) {

        // array of columns to fetch
        String[] columns = {
                DatabaseHelper.COLUMN_USER_ID
        };
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // selection criteria
        String selection = DatabaseHelper.COLUMN_USER_NAME + " = ?" + " AND " + DatabaseHelper.COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {uname, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

}
