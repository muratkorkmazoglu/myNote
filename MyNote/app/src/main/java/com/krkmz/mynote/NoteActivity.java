package com.krkmz.mynote;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
    RelativeLayout tagRl;
    private NoteModel noteModelIntent;
    private Button saveButton;
    private boolean tiklandi = false;
    private boolean changed = false;
    private boolean imageChanged = false;
    private boolean blankImage = false;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    final int YOUR_SELECT_PICTURE_REQUEST_CODE = 100;
    final int SELECT_PICTURE = 22;
    private Uri picUri;
    private Bitmap bitmap;
    private AlertDialog.Builder alertadd;
    private String fileName;
    private ActionMode actionMode;
    private Toolbar mToolbar;
    private ListView listView;
    private AlertDialog.Builder dialog;
    private TextView textViewRl;
    private ImageView barrow, imageRl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        imageView = (ImageView) findViewById(R.id.imgSave);
        tagRl = (RelativeLayout) findViewById(R.id.tagRl);
        mToolbar = (Toolbar) findViewById(R.id.toolbar3);
        textViewRl = (TextView) findViewById(R.id.textRL);
        imageRl = (ImageView) findViewById(R.id.imageVRl);
        barrow = (ImageView) findViewById(R.id.imageBarrow);

        noteModelIntent = (NoteModel) getIntent().getSerializableExtra("myModel");
        saveButton = (Button) findViewById(R.id.saveButton);
        getSupportActionBar().setElevation(0);
        changeText(1);

        if (noteModelIntent != null) {

            etTitle.setText(noteModelIntent.getTitle().toString());
            etContent.setText(noteModelIntent.getContent().toString());
            if (noteModelIntent.getDirectory() != null) {
                bitmap = loadImageBitmap(getApplicationContext(), noteModelIntent.getDirectory());
                imageView.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);
            } else {
                blankImage = true;
            }
            int id=0;
            switch (noteModelIntent.getTheme()) {
                case "GENEl":
                    id = 1;
                    break;
                case "KİŞİSEL":
                    id = 2;
                    break;
                case "İŞ":
                    id = 3;
                    break;
                case "OKUL":
                    id = 4;
                    break;
                case "EV":
                    id = 5;
                    break;
                case "DİĞER":
                    id = 6;
                    break;
            }
            changeText(id);

             Log.d("TEHEMEEE",noteModelIntent.getTheme().toString());

            etTitle.setEnabled(false);
            etContent.setEnabled(false);
            imageView.setEnabled(false);
        }
        tagRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ListModel> listArray = new ArrayList<ListModel>();
                ListModel listModel1 = new ListModel("Genel", R.mipmap.genel);
                ListModel listModel2 = new ListModel("Kişisel", R.mipmap.kisisel);
                ListModel listModel3 = new ListModel("İş", R.mipmap.is);
                ListModel listModel4 = new ListModel("Okul", R.mipmap.okul);
                ListModel listModel5 = new ListModel("Ev", R.mipmap.ev);
                ListModel listModel6 = new ListModel("Diğer", R.mipmap.diger);
                listArray.add(listModel1);
                listArray.add(listModel2);
                listArray.add(listModel3);
                listArray.add(listModel4);
                listArray.add(listModel5);
                listArray.add(listModel6);

                listView = new ListView(getApplicationContext());

                final CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), listArray);
                listView.setAdapter(adapter);


                dialog = new AlertDialog.Builder(NoteActivity.this);
                dialog.setView(listView);
                dialog.setTitle("Etiket Seçin");
                dialog.setCancelable(true);

                final AlertDialog show = dialog.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int id = 0;
                        TextView textView = (TextView) view.findViewById(R.id.tvItem);
                        String selectedText = textView.getText().toString();
                        Log.d("SELECTEDTEXT", selectedText.toString());
                        switch (selectedText) {
                            case "Genel":
                                id = 1;
                                break;
                            case "Kişisel":
                                id = 2;
                                break;
                            case "İş":
                                id = 3;
                                break;
                            case "Okul":
                                id = 4;
                                break;
                            case "Ev":
                                id = 5;
                                break;
                            case "Diğer":
                                id = 6;
                                break;
                        }
                        changeText(id);
                        show.dismiss();

                    }
                });
            }
        });

        eTChangeListener(etTitle);
        eTChangeListener(etContent);

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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KayıtEkle();
            }
        });

    }

    private void changeText(int id) {
        switch (id) {
            case 1:
                imageRl.setImageResource(R.mipmap.genel);
                textViewRl.setText("GENEL");
                barrow.setImageResource(R.mipmap.mavi);
                textViewRl.setTextColor(getResources().getColor(R.color.mavi));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.mavi)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.mavi));
                break;
            case 2:
                imageRl.setImageResource(R.mipmap.kisisel);
                textViewRl.setText("KİŞİSEL");
                barrow.setImageResource(R.mipmap.kirmizi);
                textViewRl.setTextColor(getResources().getColor(R.color.kirmizi));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.kirmizi)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.kirmizi));
                break;
            case 3:
                imageRl.setImageResource(R.mipmap.is);
                textViewRl.setText("İŞ");
                barrow.setImageResource(R.mipmap.turkuaz);
                textViewRl.setTextColor(getResources().getColor(R.color.turkuaz));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.turkuaz)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.turkuaz));
                break;
            case 4:
                imageRl.setImageResource(R.mipmap.okul);
                textViewRl.setText("OKUL");
                barrow.setImageResource(R.mipmap.yesil);
                textViewRl.setTextColor(getResources().getColor(R.color.yesil));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.yesil)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.yesil));

                break;
            case 5:
                imageRl.setImageResource(R.mipmap.ev);
                textViewRl.setText("EV");
                barrow.setImageResource(R.mipmap.sari);
                textViewRl.setTextColor(getResources().getColor(R.color.sari));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.sari)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.sari));
                break;
            case 6:
                imageRl.setImageResource(R.mipmap.diger);
                textViewRl.setText("DİĞER");
                barrow.setImageResource(R.mipmap.turuncu);
                textViewRl.setTextColor(getResources().getColor(R.color.turuncu));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.turuncu)));
                mToolbar.setBackgroundColor(getResources().getColor(R.color.turuncu));
                break;
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
//            case R.id.save:
//                KayıtEkle();
//                break;

            case R.id.updateAll:

                etTitle.setEnabled(true);
                etContent.setEnabled(true);
                imageView.setEnabled(true);

                MyActionModeCallBack callBack = new MyActionModeCallBack();
                actionMode = startSupportActionMode(callBack);

                tiklandi = true;
                break;

            case R.id.share:
                String message = etTitle.getText().toString() + "\n" + etContent.getText().toString();
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
                noteModel.setTheme(textViewRl.getText().toString());

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

