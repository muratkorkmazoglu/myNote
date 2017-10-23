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

public class NewPasswordActivity extends AppCompatActivity {

    private String password;
    private TextView createText;
    PatternLockView mPatternLockView;
    private String firstPassword = null, secondPassword = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password_layout);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        createText = (TextView) findViewById(R.id.createText);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        password = preferences.getString("password", "0");
        createText.setText("Lütfen Eski Şifreyi Giriniz");

        setPattern();

    }

    public void setPattern() {

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                System.out.println("asdasdasdas");
                if (password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {


                    createText.setText("Lütfen Yeni Deseni Giriniz");
                    mPatternLockView.clearPattern();
                    mPatternLockView.removePatternLockListener(this);
                    setPassword();

                } else {
                    createText.setText("Şifre Doğru Değil");
                    mPatternLockView.clearPattern();
                }
            }

            @Override
            public void onCleared() {

            }
        });
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
                    createText.setText("Lütfen Deseni Tekrar Giriniz");

                } else {

                    secondPassword = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    if (firstPassword.equals(secondPassword)) {
                        System.out.print("3 \n");
                        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                        editor.apply();
                        Intent ıntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(ıntent);
                        finish();

                    } else {
                        firstPassword = null;
                        createText.setText("Şifreleriniz Uyuşmadı Tekrar Yeni Şifre Giriniz.");

                    }

                }

                mPatternLockView.clearPattern();
                mPatternLockView.removePatternLockListener(this);
                setPassword();
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
