package com.example.foodreciple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.foodreciple.Model.User;

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

    public User Login_admin(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM Users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String uname = cursor.getString(1);
            String pwd = cursor.getString(2);
            String email = cursor.getString(3);
            int phanquyen = cursor.getInt(4);
            return new User(id, uname, pwd, email, phanquyen);
        } else {
            return null;
        }
    }

//    public Boolean Login(String username, String password){
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        Cursor cursor = MyDatabase.rawQuery("Select * from Users where username = ? and password = ?", new String[]{username, password});
//        if (cursor.getCount() > 0) {
//            return true;
//        }else {
//            return false;
//        }
//    }

    public Boolean SingUp(String username, String password, String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
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

    //SELECT FOOD
    public Cursor getDataFood(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodDetails WHERE UserID = 1", null);
        return cursor;
    }

    // SELECT MÓN ĂN CHƯA DUYỆT
    public Cursor getDataFood_INACTIVE(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodDetails WHERE UserID = 0", null);
        return cursor;
    }

    // SELECT MÓN ĂN THEO DANH MỤC
    public Cursor getDataFoodByCategory(int categoryId) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodDetails WHERE CategoryID = ?", new String[]{String.valueOf(categoryId)});
        return cursor;
    }


    // SELCET DANH MUC
    public Cursor getDataCategory_INACTIVE(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodCategories ", null);
        return cursor;
    }
    // UPDATE DANH MỤC
    public boolean updateCategory(int categoryID, String categoryName, byte[] imageCategory) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CategoryName", categoryName);
        contentValues.put("Image", imageCategory); // Giữ kiểu dữ liệu của imageCategory là byte[]
        long result = database.update("FoodCategories", contentValues, "ID = ?", new String[]{String.valueOf(categoryID)});
        return result != -1;
    }


    //XÓA DANH MỤC
    public boolean deleteCategory(int categoryID) {
        SQLiteDatabase database = getWritableDatabase();
        int result = database.delete("FoodCategories", "ID = ?", new String[]{String.valueOf(categoryID)});
        return result > 0;
    }
    // Cập nhật món ăn
    public boolean updateFood(int foodID, String foodName, String ingredients, String steps, String time, int bestFood, int userID) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FoodName", foodName);
        contentValues.put("Ingredients", ingredients);
        contentValues.put("Steps", steps);
        contentValues.put("Time", time);
        contentValues.put("BestFood", bestFood);
        contentValues.put("UserID", userID);

        // Update the row with the specified ID
        long result = database.update("FoodDetails", contentValues, "ID = ?", new String[]{String.valueOf(foodID)});
        return result != -1;
    }


    //Xoa Mon an
    public boolean deleteFood(int foodID) {
        SQLiteDatabase database = getWritableDatabase();
        int result = database.delete("FoodDetails", "ID = ?", new String[]{String.valueOf(foodID)});
        return result > 0;
    }




    //SELECT BEST FOOD
    public Cursor getDataBestFood(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM FoodDetails WHERE BestFood = 1", null);
        return cursor;
    }

    //SELECT IMAGE FOOD
    public Cursor getDataImage() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT Image FROM FoodDetails", null);
        return cursor;
    }

    //INSERT FOOD
    public void insertDataFood(String FoodName, int CategoryID, String Ingredients, String Steps, byte[] Image, String Time){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FoodDetails (FoodName, CategoryID, Ingredients, Steps, Image, Time, BestFood, UserID) VALUES (?, ?, ?, ?, ?, ?, 0, 0)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, FoodName);
        statement.bindLong(2, CategoryID);
        statement.bindString(3, Ingredients);
        statement.bindString(4, Steps);
        statement.bindBlob(5, Image);
        statement.bindString(6, Time);
        statement.executeInsert();
    }




}
