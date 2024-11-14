package com.example.studentserviceclintdemo.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.studentserviceclintdemo.model.LoginModel;

import java.util.ArrayList;

public class LocalDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LocalDb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOGIN = "login_info";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String PASSWORD = "user_password";

    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_LOGIN+"(\n" +
                PHONE_NUMBER+" VARCHAR(15) PRIMARY KEY NOT NULL,\n" +
                PASSWORD+" VARCHAR(30));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addLoginInfo(String phone,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE_NUMBER,phone);
        values.put(PASSWORD,password);

        db.insert(TABLE_LOGIN,null,values);
    }

    public ArrayList<LoginModel> find_login_info()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LOGIN,null);

        ArrayList<LoginModel> login_user = new ArrayList<>();

        while (cursor.moveToNext())
        {
            LoginModel loginModel = new LoginModel();
            loginModel.setPhone(cursor.getString(0));
            loginModel.setPassword(cursor.getString(1));

            login_user.add(loginModel);
        }
        return login_user;
    }

    public void delete_login_info()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "DELETE FROM "+TABLE_LOGIN+";";
        db.execSQL(sql);
    }
}
