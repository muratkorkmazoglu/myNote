package com.krkmz.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TagActivity extends AppCompatActivity {

    private List<NoteModel> modelList;
    private int genel = 0, kisisel = 0, is = 0, okul = 0, ev = 0, diger = 0;
    private TextView tvGenelCount, tvKisiselCount, tvIsCount, tvEvCount, tvOkulCount, tvDigerCount;
    private DataBase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_layout);
        tvGenelCount = (TextView) findViewById(R.id.tvThemeCountGenel);
        tvKisiselCount = (TextView) findViewById(R.id.tvThemeCountKisisel);
        tvIsCount = (TextView) findViewById(R.id.tvThemeCountIs);
        tvEvCount = (TextView) findViewById(R.id.tvThemeCountEv);
        tvOkulCount = (TextView) findViewById(R.id.tvThemeCountOkul);
        tvDigerCount = (TextView) findViewById(R.id.tvThemeCountDiger);
        db= new DataBase(TagActivity.this);
        modelList = new ArrayList<NoteModel>();

       CountNote();
    }
    public void CountNote(){

        modelList = db.tumKayitlariGetir();

        for (int k = 0; k < modelList.size(); k++) {
            if (modelList.get(k).getTheme().equals("GENEL")) {
                genel++;
            } else if (modelList.get(k).getTheme().equals("KİŞİSEL")) {
                kisisel++;
            } else if (modelList.get(k).getTheme().equals("İŞ")) {
                is++;
            } else if (modelList.get(k).getTheme().equals("EV")) {
                ev++;
            } else if (modelList.get(k).getTheme().equals("OKUL")) {
                okul++;
            } else if (modelList.get(k).getTheme().equals("DİĞER")) {
                diger++;
            }
        }
        tvGenelCount.setText(String.valueOf(genel));
        tvKisiselCount.setText(String.valueOf(kisisel));
        tvIsCount.setText(String.valueOf(is));
        tvEvCount.setText(String.valueOf(ev));
        tvOkulCount.setText(String.valueOf(okul));
        tvDigerCount.setText(String.valueOf(diger));
    }

    public void tiklandi(View v) {

        Intent ıntent = new Intent(TagActivity.this, ChooseActivity.class);
        switch (v.getId()) {
            case R.id.rlGenel:
                ıntent.putExtra("myTag", "GENEL");
                break;
            case R.id.rlKisisel:
                ıntent.putExtra("myTag", "KİŞİSEL");
                break;
            case R.id.rlEv:
                ıntent.putExtra("myTag", "EV");
                break;
            case R.id.rlOkul:
                ıntent.putExtra("myTag", "OKUL");
                break;
            case R.id.rlIs:
                ıntent.putExtra("myTag", "İŞ");
                break;
            case R.id.rlDiger:
                ıntent.putExtra("myTag", "DİĞER");
                break;
        }
        startActivity(ıntent);
        finish();
    }

}
