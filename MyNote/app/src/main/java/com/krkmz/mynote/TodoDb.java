package com.krkmz.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by muratkorkmazoglu on 31.10.2017.
 */

public class TodoDb  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tododb";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "todo_table";

    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String ITEM = "item";
    private static final String ISCHECKED = "ischecked";

    public TodoDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                ITEM + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long kayitEkle(String ListTitle, String todoModel) {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE,ListTitle);
        cv.put(ITEM,todoModel.toString());


        long id = db.insert(TABLE_NAME, null, cv);
        db.close();

        return id;
    }


    public List<HashMap<String,String>> tumKayitlariGetir() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] sutunlar = new String[]{TITLE,ITEM};
        Cursor c = db.query(TABLE_NAME,  sutunlar , null, null, null, null, null);

        int titleNo = c.getColumnIndex(TITLE);
        int itemNameNo = c.getColumnIndex(ITEM);

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String titleName = c.getString(titleNo);
            String Items = c.getString(itemNameNo);
            HashMap<String,String> hashMap = new HashMap<String, String>();

            hashMap.put("titleName",titleName);
            hashMap.put("Item",Items);

            arrayList.add(hashMap);

        }
        db.close();
        return arrayList;

    }
    public void Sil() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

}
