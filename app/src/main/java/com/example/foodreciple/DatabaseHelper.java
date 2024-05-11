package com.example.foodreciple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "FoodRecipleData.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    private String SQLQuery_Table_Users = "CREATE TABLE Users (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT UNIQUE, password TEXT, email TEXT, phanquyen INTEGER)";

    private String SQLQuery_Table_Categories = "CREATE TABLE FoodCategories (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "CategoryName TEXT, Image BLOB);";

    private String SQLQuery_Table_Details = "CREATE TABLE FoodDetails (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FoodName TEXT, CategoryID INTEGER, Ingredients TEXT, Steps TEXT, Image BLOB, UserID INTEGER, " +
            "Time TEXT, BestFood INTEGER, FOREIGN KEY (CategoryID) REFERENCES FoodCategories (ID), " +
            "FOREIGN KEY (UserID) REFERENCES Users (ID));";

    private String SQLQuery1 = "INSERT INTO Users VAlUES (null,'admin','admin','admin@gmail.com',2)";
    private String SQLQuery2 = "INSERT INTO Users VAlUES (null,'huy','huy','huy@gmail.com',1)";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery_Table_Users);
        db.execSQL(SQLQuery_Table_Categories);
        db.execSQL(SQLQuery_Table_Details);
        db.execSQL(SQLQuery1);
        db.execSQL(SQLQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Users");
        onCreate(db);

    }

    public Boolean Login(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from Users where username = ? and password = ?", new String[]{username, password});
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
        long result = MyDatabase.insert("Users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from Users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkUser(String username){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from Users where username = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    // ADMIN
    // INSERT CATEGORIES
    public void insertData(String CategoryName, byte[] Image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FoodCategories VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, CategoryName);
        statement.bindBlob(2, Image);
        statement.executeInsert();
    }
    //SELECT CATEGORY
    public Cursor getDataCategory(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodCategories", null);
        return cursor;
    }

    //INSERT FOOD
    public void insertDataFood(String FoodName, int CategoryID, byte[] Image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FoodDetails (FoodName, CategoryID, Image) VALUES (?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, FoodName);
        statement.bindLong(2, CategoryID);
        statement.bindBlob(3, Image);
        statement.executeInsert();
    }


}
