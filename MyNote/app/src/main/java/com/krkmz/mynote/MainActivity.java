package com.krkmz.mynote;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import java.text.SimpleDateFormat;
import java.util.Date;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import hotchemi.android.rate.StoreType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView newNote, allNote, tagImage, searchImage;
    private String password;
    private AdView adView;
    private InterstitialAd gecisReklam,interstitial;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView navView;
    private LayoutInflater inflater;
    private View layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        adView = (AdView) this.findViewById(R.id.adView);
        navView = (NavigationView) findViewById(R.id.nav_view);
        newNote = (ImageView) findViewById(R.id.newNote);
        allNote = (ImageView) findViewById(R.id.allNotes);
        tagImage = (ImageView) findViewById(R.id.tags);
        searchImage = (ImageView) findViewById(R.id.searchNote);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        password = preferences.getString("password", "0");

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView.setNavigationItemSelectedListener(this);
        inflater = LayoutInflater.from(this);

        final AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("35CE1B04A529F242D2AA916EB233056F").build();
        adView.loadAd(adRequest);

        interstitial=new InterstitialAd(MainActivity.this);
        interstitial.setAdUnitId("ca-app-pub-3924428861396897/5880807524");

        gecisReklam = new InterstitialAd(this);
        gecisReklam.setAdUnitId("ca-app-pub-3924428861396897/5880807524");
        gecisReklam.loadAd(adRequest);
        gecisReklam.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Intent ıntent = new Intent(getApplicationContext(), NoteActivity.class);
                        startActivity(ıntent);
                    }
                });
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

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            case R.id.updatePassword:
                Intent ıntent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                ıntent.putExtra("password", password);
                startActivity(ıntent);
                MainActivity.this.finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showInterstitial() {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
                AppRate.with(MainActivity.this)
                        .setStoreType(StoreType.GOOGLEPLAY) //default is Google, other option is Amazon
                        .setInstallDays(3) // default 10, 0 means install day.
                        .setLaunchTimes(10) // default 10 times.
                        .setRemindInterval(2) // default 1 day.
                        .setShowLaterButton(true) // default true.
                        .setDebug(true) // default false.
                        .setCancelable(false) // default false.
                        .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                            @Override
                            public void onClickButton(int which) {
                                Log.d(MainActivity.class.getName(), Integer.toString(which));
                            }
                        })
                        .setTitle("Oy Ver")
                        .setTextLater("Daha Sonra Hatırlat")
                        .setTextNever("Asla")
                        .setTextRateNow("Tamam")
                        .monitor();

                AppRate.showRateDialogIfMeetsConditions(MainActivity.this);
                drawerLayout.closeDrawer(Gravity.LEFT);

                break;
            case R.id.contact:

                layout = inflater.inflate(R.layout.contact_dialog, null);
                final EditText subject, message;
                subject = layout.findViewById(R.id.subject_text);
                message = layout.findViewById(R.id.messaje_text);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setView(layout);
                dialog.setTitle("İletişim");
                dialog.setPositiveButton("Gönder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String to = "muratkorkmazoglu@gmail.com";
                        String sub = subject.getText().toString();
                        String mesaj = message.getText().toString();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                        intent.putExtra(Intent.EXTRA_SUBJECT, sub);
                        intent.putExtra(Intent.EXTRA_TEXT, mesaj);

                        intent.setType("message/rfc822");

                        startActivity(Intent.createChooser(intent, "Lütfen Bir Mail Uygulaması Seçiniz"));


                    }
                });
                dialog.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();
                drawerLayout.closeDrawer(Gravity.LEFT);


                break;

            case R.id.share:

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My Note");
                    String sAux = "\nBu Uygulamayı Severek Kullanıyorum ve Sanada Tavsiye Ediyorum.\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Paylaş"));
                } catch (Exception e) {
                    e.toString();
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

        }
        return true;
    }
}
