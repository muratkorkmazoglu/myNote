package com.krkmz.mynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private ImageView newNote, allNote, tagImage, searchImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(System.currentTimeMillis());
        System.out.println(sdf.format(resultdate));

        newNote = (ImageView) findViewById(R.id.newNote);
        allNote = (ImageView) findViewById(R.id.allNotes);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(ıntent);
            }
        });


        allNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(), ChooseActivity.class);
                startActivity(ıntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.updatePassword) {
            Intent ıntent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
            ıntent.putExtra("value","dolu");
            startActivity(ıntent);
            MainActivity.this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
