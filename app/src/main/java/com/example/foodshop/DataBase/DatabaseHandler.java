package com.example.foodshop.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.foodshop.Constants.Constants;
import com.example.foodshop.Model.Grocery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_GROCERY_ID + " TEXT,"
                + Constants.KEY_GROCERY_ITEM + " TEXT,"
                + Constants.KEY_GROCERY_PRICE + " TEXT,"
                + Constants.KEY_GROCERY_CATEGORY + " TEXT,"
                + Constants.KEY_GROCERY_DESCRIPTION + " TEXT,"
                + Constants.KEY_DATE + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ID, grocery.getItemID());
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_GROCERY_PRICE, grocery.getPrice());
        values.put(Constants.KEY_GROCERY_CATEGORY, grocery.getCategory());
        values.put(Constants.KEY_GROCERY_DESCRIPTION, grocery.getDescription());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved", "Saved to db" + values);
    }

    public void updateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ID, grocery.getItemID());
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_GROCERY_PRICE, grocery.getPrice());
        values.put(Constants.KEY_GROCERY_CATEGORY, grocery.getCategory());
        values.put(Constants.KEY_GROCERY_DESCRIPTION, grocery.getDescription());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[]{
                String.valueOf(grocery.getId())
        });
    }

    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public List<Grocery> getAllGrocery() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID,
                Constants.KEY_GROCERY_ITEM,
                Constants.KEY_GROCERY_ID,
                Constants.KEY_GROCERY_PRICE,
                Constants.KEY_GROCERY_CATEGORY,
                Constants.KEY_GROCERY_DESCRIPTION,
                Constants.KEY_DATE
        }, null, null, null, null, Constants.KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setItemID(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ID)));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setPrice(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_PRICE)));
                grocery.setCategory(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_CATEGORY)));
                grocery.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_DESCRIPTION)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))));
                grocery.setDate(formatDate);

                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }
        return groceryList;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllIds() {
        ArrayList<String> idList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(Constants.KEY_ID));
                idList.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return idList;
    }

    public String selectCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM groceryTBL WHERE groceryCATEGORY=?";
        Cursor cursor = db.rawQuery(query, new String[]{category});

        String result = "";
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            result = "В списъка има " + count + " артикул/а от категория " + category;
        }
        cursor.close();
        db.close();

        return result;
    }

    //TODO:
    //Функция за създаване на таблица с Foreign key към старата таблица за достъпване само на името и цената на продукт
    //които са вече въведени.
    public String addToShopCart() {

        return null;
    }

    //TODO:
    //Функция за взимане на данните от 2та таблица
    public String getFromShopCart() {

        return null;
    }
}
