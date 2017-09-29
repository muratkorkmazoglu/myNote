package com.krkmz.mynote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class NoteActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private NoteModel noteModelIntent;
    private boolean tiklandi = false;
    private boolean changed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);

        noteModelIntent = (NoteModel) getIntent().getSerializableExtra("myModel");

        if (noteModelIntent != null) {
            etTitle.setText(noteModelIntent.getTitle().toString());
            etContent.setText(noteModelIntent.getContent().toString());
            etTitle.setEnabled(false);
            etContent.setEnabled(false);
        }
        eTChangeListener(etTitle);
        eTChangeListener(etContent);



    }

    private void guncelle() {
        DataBase db = new DataBase(getApplicationContext());
        db.Guncelle(noteModelIntent.getId(), System.currentTimeMillis(), etTitle.getText().toString(), etContent.getText().toString());
    }

    private void eTChangeListener(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (noteModelIntent != null) {
            getMenuInflater().inflate(R.menu.menu_note_activity_fill, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_note_activity, menu);
            return true;
        }

    }

    @Override
    public void onBackPressed() {

        if (changed){
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
            builder.setTitle("Kayıt");
            builder.setMessage("Değişiklikler Kaydedilsin Mi?");
            builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id1) {
                  guncelle();
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                DataBase db = new DataBase(getApplicationContext());
                if (tiklandi) {

                  guncelle();
                    Toast.makeText(getApplicationContext(), "Kayıt Güncellendi", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    if (!etTitle.getText().toString().trim().equals("") || !etContent.getText().toString().trim().equals("")) {

                        NoteModel noteModel = new NoteModel(System.currentTimeMillis(), etTitle.getText().toString(), etContent.getText().toString());

                        long id = db.kayitEkle(noteModel);
                        if (id == -1) {
                            Toast.makeText(getApplicationContext(), "Kayıt Sırasında Bir Hata Oluştu", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarılı", Toast.LENGTH_LONG).show();
                        }
                        finish();
                    } else if (etTitle.getText().toString().trim().equals("") && etContent.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "En az bir değer girmelisiniz ", Toast.LENGTH_LONG).show();
                    }

                }



                break;

            case R.id.update:

                etTitle.setEnabled(true);
                etContent.setEnabled(true);
                tiklandi = true;
                break;
            case R.id.deleteFill:

                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("Kayıt Silinecek");
                builder.setMessage("Emin Misiniz ?");
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id1) {
                        DataBase db = new DataBase(getApplicationContext());
                        db.Sil(noteModelIntent.getId());

                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                DataBase dataBase = new DataBase(getApplicationContext());
                dataBase.Sil(noteModelIntent.getId());

                break;

        }

        return true;
    }



}

