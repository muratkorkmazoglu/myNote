package com.krkmz.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "note_table";

    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String DATE = "tarih";
    private static final String DIRECTORY = "directory";
    private static final String THEME = "theme";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CONTENT + " TEXT, " +
                THEME + " TEXT, " +
                DATE + " INTEGER, " +
                DIRECTORY + " TEXT);");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public long kayitEkle(NoteModel noteModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, noteModel.getTitle());
        cv.put(CONTENT, noteModel.getContent());
        cv.put(DATE, noteModel.getDateTime());
        cv.put(THEME, noteModel.getTheme());

        if (noteModel.getDirectory() != null) {
            cv.put(DIRECTORY, noteModel.getDirectory());
        }

        long id = db.insert(TABLE_NAME, null, cv);
        db.close();

        return id;
    }

    public List<NoteModel> tumKayitlariGetir() {

        SQLiteDatabase db = this.getReadableDatabase();
        //String[] sutunlar = new String[]{ID, TITLE, CONTENT, DATE, DIRECTORY};
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);

        int titleNo = c.getColumnIndex(TITLE);
        int contentNo = c.getColumnIndex(CONTENT);
        int dateNo = c.getColumnIndex(DATE);
        int idNo = c.getColumnIndex(ID);
        int directoryNo = c.getColumnIndex(DIRECTORY);
        int themeNo = c.getColumnIndex(THEME);

        List<NoteModel> modelList = new ArrayList<NoteModel>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            NoteModel noteModel = new NoteModel();
            noteModel.setTitle(c.getString(titleNo));
            noteModel.setContent(c.getString(contentNo));
            noteModel.setDateTime(c.getInt(dateNo));
            noteModel.setId(c.getInt(idNo));

            if (c.getString(themeNo)!=null){
                noteModel.setTheme(c.getString(themeNo));
            }



            if (c.getString(directoryNo) != null) {
                noteModel.setDirectory(c.getString(directoryNo));
            }

            modelList.add(noteModel);

        }
        db.close();
        return modelList;

    }

    public void Guncelle(NoteModel model) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, model.getTitle());
        cv.put(CONTENT, model.getContent());
        cv.put(DATE, model.getDateTime());

        if (model.getDirectory() != null) {
            cv.put(DIRECTORY, model.getDirectory());
        }
        if (model.getTheme() != null) {
            cv.put(THEME, model.getTheme());
        }

        db.update(TABLE_NAME, cv, ID + "=" + model.getId(), null);
        db.close();
    }

    public void Sil(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + id, null);
        db.close();
    }

    public List<NoteModel> getTheme(String theme) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + THEME + " = ? ", new String[]{theme});
        int titleNo = c.getColumnIndex(TITLE);
        int contentNo = c.getColumnIndex(CONTENT);
        int dateNo = c.getColumnIndex(DATE);
        int idNo = c.getColumnIndex(ID);
        int directoryNo = c.getColumnIndex(DIRECTORY);
        int themeNo = c.getColumnIndex(THEME);

        List<NoteModel> modelList = new ArrayList<NoteModel>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            NoteModel noteModel = new NoteModel();
            noteModel.setTitle(c.getString(titleNo));
            noteModel.setContent(c.getString(contentNo));
            noteModel.setDateTime(c.getInt(dateNo));
            noteModel.setId(c.getInt(idNo));
            noteModel.setTheme(c.getString(themeNo));


            if (c.getString(directoryNo) != null) {
                noteModel.setDirectory(c.getString(directoryNo));
            }

            modelList.add(noteModel);

        }


        db.close();
        return modelList;
    }

    public void Sil() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
