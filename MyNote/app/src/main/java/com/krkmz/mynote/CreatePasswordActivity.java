package com.krkmz.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;


public class CreatePasswordActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    private String firstPassword = null, secondPassword = null;
    private TextView createText;
    private boolean kontrol=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password_layout);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        createText = (TextView) findViewById(R.id.createText);
        setPassword();
        if (kontrol){
            firstPassword = null;
            secondPassword = null;
            mPatternLockView.clearPattern();
            setPassword();
        }
    }

    public void setPassword() {

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
                if (firstPassword != null && secondPassword != null){

                    if (firstPassword.equals(secondPassword)){
                        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                        editor.apply();

                        Intent ıntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(ıntent);
                        finish();
                    }else {
                        Log.d("FARJKLIFARKIL", "FARKLI");
                        createText.setText("Desenler Eşleşmedi. Lütfen Tekrar Deneyiniz");
                        mPatternLockView.clearPattern();
                        kontrol=true;
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
