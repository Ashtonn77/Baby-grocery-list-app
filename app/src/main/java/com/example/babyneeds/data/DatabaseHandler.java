package com.example.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.babyneeds.model.Item;
import com.example.babyneeds.utils.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private final Context context;

    public DatabaseHandler(@Nullable Context context)
    {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                                    + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                                    + Constants.KEY_GROCERY_ITEM + " TEXT,"//change to integer if errors
                                    + Constants.KEY_COLOR + " TEXT,"
                                    + Constants.KEY_ITEM_QTY + " INTEGER,"
                                    + Constants.KEY_ITEM_SIZE + " INTEGER,"
                                    + Constants.KEY_DATE + " LONG);";

        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD
    public void addItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_GROCERY_ITEM, item.getItemName());
        values.put(Constants.KEY_ITEM_QTY, item.getItemQuantity());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        Log.d("DBHandler", "item added");

    }

    public Item getItem(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]
                        {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_COLOR,
                        Constants.KEY_ITEM_QTY, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE},
                        Constants.KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null);

        if(cursor != null) { cursor.moveToFirst(); }

        Item item = new Item();

        if(cursor != null)
        {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            item.setItemColor((cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR))));
            item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

            //convert timestamp
            DateFormat dateFormat = DateFormat.getInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());

            item.setDateAdded(formattedDate);
        }

     return item;

    }

    public List<Item> getAllItems()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]
                        {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_COLOR,
                        Constants.KEY_ITEM_QTY, Constants.KEY_ITEM_SIZE, Constants.KEY_DATE},
                null, null, null, null, Constants.KEY_DATE + " DESC");

        if(cursor.moveToFirst())
        {
            do {

                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                item.setItemColor((cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR))));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

                //convert timestamp
                DateFormat dateFormat = DateFormat.getInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());

                item.setDateAdded(formattedDate);

                itemList.add(item);

            }while(cursor.moveToNext());
        }
        return itemList;
    }

    public int updateItem(Item item)
    {

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_GROCERY_ITEM, item.getItemName());
        values.put(Constants.KEY_ITEM_QTY, item.getItemQuantity());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[]{String.valueOf(item.getId())});

    }

    public void deleteItem(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{String.valueOf(id)});

        db.close();

    }

    public int getItemsCount()
    {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}
