package com.krkmz.mynote;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class NoteActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private ImageView imageView;
    private NoteModel noteModelIntent;
    private boolean tiklandi = false;
    private boolean changed = false;
    private boolean imageChanged = false;
    private boolean blankImage = false;
    private Button button;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    final int YOUR_SELECT_PICTURE_REQUEST_CODE = 100;
    final int SELECT_PICTURE = 22;
    private Uri picUri;
    private Bitmap bitmap;
    private AlertDialog.Builder alertadd;
    private String fileName;
    private ActionMode actionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        button = (Button) findViewById(R.id.voice);
        imageView = (ImageView) findViewById(R.id.imgSave);
        noteModelIntent = (NoteModel) getIntent().getSerializableExtra("myModel");

        if (noteModelIntent != null) {

            etTitle.setText(noteModelIntent.getTitle().toString());
            etContent.setText(noteModelIntent.getContent().toString());
            if (noteModelIntent.getDirectory() != null) {
                bitmap = loadImageBitmap(getApplicationContext(), noteModelIntent.getDirectory());
                imageView.setImageBitmap(bitmap);
            } else {
                blankImage = true;
            }


            etTitle.setEnabled(false);
            etContent.setEnabled(false);
            imageView.setEnabled(false);
        }

        eTChangeListener(etTitle);
        eTChangeListener(etContent);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMic();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap != null) {

                    alertadd = new AlertDialog.Builder(NoteActivity.this);
                    LayoutInflater factory = LayoutInflater.from(NoteActivity.this);
                    final View view1 = factory.inflate(R.layout.sample, null);
                    ImageView imageview;
                    imageview = view1.findViewById(R.id.dialog_imageview);
                    imageview.setImageBitmap(bitmap);
                    alertadd.setView(view1);
                    alertadd.show();

                }
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder getImageFrom = new AlertDialog.Builder(NoteActivity.this);
                getImageFrom.setTitle("İşlem Seçiniz");
                final CharSequence[] opsChars = {"Değiştir", "Sil"};
                getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            ChangeImage();
                            imageChanged = true;

                        } else if (which == 1) {

                            File dir = getFilesDir();
                            File file = new File(dir, noteModelIntent.getDirectory());
                            file.delete();
                            imageView.setImageBitmap(null);

                        }
                        dialog.dismiss();
                    }
                });
                getImageFrom.show();

                return false;
            }
        });

    }

    private void openMic() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Şimdi Konuşun");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException tim) {

        }

    }

    private void guncelle() {

        DataBase db = new DataBase(getApplicationContext());
        NoteModel model = new NoteModel();
        model.setId(noteModelIntent.getId());
        model.setDateTime(System.currentTimeMillis());
        model.setTitle(etTitle.getText().toString());
        model.setContent(etContent.getText().toString());

        if (imageChanged) {
            saveImage(getApplicationContext(), bitmap, fileName + ".jpeg");
            model.setDirectory(fileName + ".jpeg");
        }
        if (blankImage) {
            Log.d("BLANKIMAGE", "BLANKIMAGE");
            fileName = getPictureName();
            saveImage(getApplicationContext(), bitmap, fileName + ".jpeg");
            model.setDirectory(fileName + ".jpeg");
        }


        db.Guncelle(model);
    }

    private void eTChangeListener(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (noteModelIntent != null) {
            getMenuInflater().inflate(R.menu.menu_note_update, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_note_activity, menu);
            return true;
        }

    }

    @Override
    public void onBackPressed() {

        if (changed) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
            builder.setTitle("Kayıt");
            builder.setMessage("Değişiklikler Kaydedilsin Mi?");
            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id1) {
                    if (noteModelIntent == null) {
                        KayıtEkle();
                        finish();
                    } else {
                        guncelle();
                        finish();
                    }

                }
            });
            builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    finish();

                }
            });


            builder.setCancelable(false);
            builder.show();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DataBase db = new DataBase(getApplicationContext());
        switch (item.getItemId()) {
            case R.id.save:
                KayıtEkle();
                break;

            case R.id.updateAll:

                etTitle.setEnabled(true);
                etContent.setEnabled(true);
                imageView.setEnabled(true);

                MyActionModeCallBack callBack = new MyActionModeCallBack();
                actionMode = startSupportActionMode(callBack);

                tiklandi = true;
                break;

            case R.id.share:
                String message = etTitle.getText().toString()+ "\n" + etContent.getText().toString();
                shareMessage(message);

                break;

            case R.id.image:
                ChangeImage();

                break;

        }

        return true;
    }

    private void shareMessage(CharSequence message) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(shareIntent, "Gönder"));

    }

    private void ChangeImage() {

        AlertDialog.Builder getImageFrom = new AlertDialog.Builder(NoteActivity.this);
        getImageFrom.setTitle("İşlem Seçiniz");
        final CharSequence[] opsChars = {"Kamera", "Galeri"};
        getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
                } else if (which == 1) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Galeri"), SELECT_PICTURE);
                }
                dialog.dismiss();
            }
        });
        getImageFrom.show();
    }

    public void KayıtEkle() {

        DataBase db = new DataBase(getApplicationContext());
        if (tiklandi) {
            guncelle();
            Toast.makeText(getApplicationContext(), "Kayıt Güncellendi", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (!etTitle.getText().toString().trim().equals("") || !etContent.getText().toString().trim().equals("")) {

                NoteModel noteModel = new NoteModel();
                noteModel.setTitle(etTitle.getText().toString());
                noteModel.setContent(etContent.getText().toString());
                noteModel.setDateTime(System.currentTimeMillis());

                if (bitmap != null) {
                    fileName = getPictureName();
                    saveImage(getApplicationContext(), bitmap, fileName + ".jpeg");
                    noteModel.setDirectory(fileName + ".jpeg");
                }

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

    }

    public void saveImage(Context context, Bitmap bitmap, String name) {

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            rotateImage(bitmap).compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPictureName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddd_HHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return "img" + timestamp.toString();
    }

    public Bitmap loadImageBitmap(Context context, String name) {

        FileInputStream fileInputStream;
        Bitmap bitmap1 = null;
        try {
            fileInputStream = context.openFileInput(name);

            //bitmap1 = BitmapFactory.decodeStream(bitmap1);
            bitmap1 = BitmapFactory.decodeStream(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etContent.setText(voiceInText.get((0)));
                }
                break;
            case YOUR_SELECT_PICTURE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();

                    try {

                        Bitmap myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        bitmap = rotateImage(myBitmap);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case SELECT_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    picUri = data.getData();


                    try {
                        Bitmap myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        bitmap = rotateImage(myBitmap);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(0);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    class MyActionModeCallBack implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_note_activity_fill, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
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
                case R.id.save:

                    guncelle();
                    finish();
                    break;
                case R.id.camera:

                    ChangeImage();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    }


}

