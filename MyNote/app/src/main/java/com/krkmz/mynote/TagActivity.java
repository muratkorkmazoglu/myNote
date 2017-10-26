package com.krkmz.mynote;

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

import java.util.ArrayList;
import java.util.List;


public class TagActivity extends AppCompatActivity {

    private List<NoteModel> modelList;
    private int genel = 0, kisisel = 0, is = 0, okul = 0, ev = 0, diger = 0;
    private TextView tvGenelCount, tvKisiselCount, tvIsCount, tvEvCount, tvOkulCount, tvDigerCount;
    private RelativeLayout genelLayout, kisiselLayout, isLayout, evLayout, okulLayout, digerLayout;

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
        genelLayout = (RelativeLayout) findViewById(R.id.rlGenel);
        kisiselLayout = (RelativeLayout) findViewById(R.id.rlKisisel);
        isLayout = (RelativeLayout) findViewById(R.id.rlIs);
        evLayout = (RelativeLayout) findViewById(R.id.rlEv);
        okulLayout = (RelativeLayout) findViewById(R.id.rlOkul);
        digerLayout = (RelativeLayout) findViewById(R.id.rlDiger);

        DataBase db = new DataBase(TagActivity.this);
        modelList = new ArrayList<NoteModel>();
        modelList = db.tumKayitlariGetir();

        for (int k = 0; k < modelList.size(); k++) {
            if (modelList.get(k).getTheme().equals("GENEL")) {
                genel++;
                Log.d("GENEL", String.valueOf(genel));
            } else if (modelList.get(k).getTheme().equals("KİŞİSEL")) {
                kisisel++;
                Log.d("KİŞİSEL", String.valueOf(kisisel));
            } else if (modelList.get(k).getTheme().equals("İŞ")) {
                is++;
                Log.d("İŞ", String.valueOf(is));
            } else if (modelList.get(k).getTheme().equals("EV")) {
                ev++;
                Log.d("EV", String.valueOf(ev));
            } else if (modelList.get(k).getTheme().equals("OKUL")) {
                okul++;
                Log.d("OKUL", String.valueOf(okul));
            } else if (modelList.get(k).getTheme().equals("DİĞER")) {
                diger++;
                Log.d("DİĞER", String.valueOf(diger));
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
        if (v.getId() == R.id.rlGenel) {
            Toast.makeText(getApplicationContext(),"Tiklandi",Toast.LENGTH_SHORT).show();
        }

    }
}
