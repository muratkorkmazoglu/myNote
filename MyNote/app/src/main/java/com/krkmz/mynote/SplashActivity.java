package com.krkmz.mynote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                SharedPreferences preferences=getSharedPreferences("PREFS",0);
                String password=preferences.getString("password","0");
                if (password.equals("0")){
                    Intent ıntent=new Intent(getApplicationContext(),CreatePasswordActivity.class);
                    startActivity(ıntent);
                    finish();
                }else {
                    Intent ıntent=new Intent(getApplicationContext(),InputPasswordActivity.class);
                    startActivity(ıntent);
                    finish();
                }

            }
        }, 1500);
    }
}
