package com.krkmz.mynote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private ImageView newNote, allNote, tagImage, searchImage;
    String password;
    private AdView adView;
    private InterstitialAd gecisReklam;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        gecisReklam = new InterstitialAd(this);

        gecisReklam.setAdUnitId("ca-app-pub-3924428861396897/5880807524");

        gecisReklam.loadAd(new AdRequest.Builder().build());

        gecisReklam.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        password = preferences.getString("password", "0");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(System.currentTimeMillis());
        System.out.println(sdf.format(resultdate));

        newNote = (ImageView) findViewById(R.id.newNote);
        allNote = (ImageView) findViewById(R.id.allNotes);
        tagImage = (ImageView) findViewById(R.id.tags);
        searchImage = (ImageView) findViewById(R.id.searchNote);

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
        tagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(), TagActivity.class);
                startActivity(ıntent);
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(), TodoActivity.class);
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
            Intent ıntent = new Intent(getApplicationContext(), NewPasswordActivity.class);
            ıntent.putExtra("password",password);
            startActivity(ıntent);
            MainActivity.this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
    public void showInterstitial(){
        if (gecisReklam.isLoaded()) {
            gecisReklam.show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }
}
