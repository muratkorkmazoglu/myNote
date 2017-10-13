package com.krkmz.mynote;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NoteModel implements Serializable {

    private long dateTime;
    private String title;
    private String content;
    private int id;
    private String directory;

    public NoteModel(long dateTime, String title, String content) {
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }

    public NoteModel(){

    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTimeFormatted(Context context){

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(dateTime));

    }
}
