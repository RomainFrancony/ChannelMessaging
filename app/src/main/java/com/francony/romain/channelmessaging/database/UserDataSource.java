package com.francony.romain.channelmessaging.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.francony.romain.channelmessaging.database.FriendsDB;
import com.francony.romain.channelmessaging.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franconr on 23/01/2017.
 */
public class UserDataSource {

    // Database fields
    private SQLiteDatabase database;
    private FriendsDB dbHelper;
    private String[] allColumns = { FriendsDB.KEY_USERID,FriendsDB.KEY_USERNAME, FriendsDB.KEY_IMAGEURL };
    public UserDataSource(Context context) {
        dbHelper = new FriendsDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public User createUser(int userid, String username, String imageurl) {
        ContentValues values = new ContentValues();
        values.put(FriendsDB.KEY_USERID, userid);
        values.put(FriendsDB.KEY_USERNAME, username);
        values.put(FriendsDB.KEY_IMAGEURL,imageurl );
        database.insert(FriendsDB.USER_TABLE_NAME, null, values);
        Cursor cursor = database.query(FriendsDB.USER_TABLE_NAME, allColumns, FriendsDB.KEY_USERID + " = \"" + Integer.toString(userid)+"\"", null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        return user;
    }


    public List<User> getAllUser() {
        List<User> lesHommes = new ArrayList<User>();
        Cursor cursor = database.query(FriendsDB.USER_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User unHomme = cursorToUser(cursor);
            lesHommes.add(unHomme);
            cursor.moveToNext();
        }
        cursor.close();
        return lesHommes;
    }

}