package com.example.foodreciple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "FoodRecipleData.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    private String SQLQuery_Table_TaiKhoan = "CREATE TABLE taikhoan (idtaikhoan INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT UNIQUE, password TEXT, email TEXT, phanquyen INTEGER)";

    private String SQLQuery1 = "INSERT INTO taikhoan VAlUES (null,'admin','admin','admin@gmail.com',2)";
    private String SQLQuery2 = "INSERT INTO taikhoan VAlUES (null,'huy','huy','huy@gmail.com',1)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery_Table_TaiKhoan);
        db.execSQL(SQLQuery1);
        db.execSQL(SQLQuery2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists taikhoan");
        onCreate(db);

    }

    public Boolean Login(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from taikhoan where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean SingUp(String username, String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("taikhoan", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from taikhoan where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkUser(String username){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from taikhoan where username = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}
