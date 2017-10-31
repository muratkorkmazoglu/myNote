package com.krkmz.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
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
                ITEM + " TEXT, " +
                ISCHECKED + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long kayitEkle(TodoModel todoModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE,todoModel.getListTitle());
        cv.put(ITEM,todoModel.getListItemsName());
        cv.put(ISCHECKED,todoModel.getChecked());

        long id = db.insert(TABLE_NAME, null, cv);
        db.close();

        return id;
    }


    public List<TodoModel> tumKayitlariGetir() {

        SQLiteDatabase db = this.getReadableDatabase();
        //String[] sutunlar = new String[]{ID, TITLE, CONTENT, DATE, DIRECTORY};
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);

        int titleNo = c.getColumnIndex(TITLE);
        int itemNameNo = c.getColumnIndex(ITEM);
        int isCheckedNo = c.getColumnIndex(ISCHECKED);
        int idNo = c.getColumnIndex(ID);


        List<TodoModel> modelList = new ArrayList<TodoModel>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            TodoModel todoModel = new TodoModel();
            todoModel.setListTitle(c.getString(titleNo));
            todoModel.setChecked(c.getString(isCheckedNo));
            todoModel.setListItemsName(c.getString(itemNameNo));


            modelList.add(todoModel);

        }
        db.close();
        return modelList;

    }

}
