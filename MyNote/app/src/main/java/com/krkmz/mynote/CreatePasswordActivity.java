package com.krkmz.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;


public class CreatePasswordActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    private String firstPassword = null, secondPassword = null;
    private TextView createText;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password_layout);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        createText = (TextView) findViewById(R.id.createText);
        Log.d("INTENT",getIntent().getStringExtra("value"));
        if (getIntent().getStringExtra("value") != null) {
            createText.setText("Lütfen Mevcut Şifreyi Giriniz");
            SharedPreferences preferences = getSharedPreferences("PREFS", 0);
            password = preferences.getString("password", "0");
            Log.d("password",password);
            PasswordControl();
        } else {
            setPassword();
        }

    }

    private void PasswordControl() {

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if (password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {
                    createText.setText("Lütfen Yeni Deseni Giriniz");
                    mPatternLockView.clearPattern();
                    ///////YENİ DESEN GİRİLİNCE NE YAPILACAKKKK
                } else {
                    Toast.makeText(getApplicationContext(), "Desen Eşleşmedi. Lütfen Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                    mPatternLockView.clearPattern();
                    PasswordControl();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }

    public void setPassword() {
        Log.d("password","GELDİİİİ----------------");
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                if (firstPassword == null) {
                    firstPassword = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    mPatternLockView.clearPattern();
                    createText.setText("Lütfen Deseni Tekrar Giriniz");
                    setPassword();
                } else if (firstPassword != null) {
                    secondPassword = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    Log.d("SECONDPASSWORD", "SECONDE");
                }

                if (firstPassword != null && secondPassword != null) {

                    if (firstPassword.equals(secondPassword)) {
                        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                        editor.apply();

                        Intent ıntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(ıntent);
                        finish();
                    } else {
                        Log.d("FARJKLIFARKIL", "FARKLI");
                        createText.setText("Desenler Eşleşmedi. Lütfen Tekrar Deneyiniz");
                        mPatternLockView.clearPattern();
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
