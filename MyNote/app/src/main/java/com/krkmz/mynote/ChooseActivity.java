package com.krkmz.mynote;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NoteModel> modelList;
    private ActionMode actionMode;
    private int id;
    private NoteAdapter noteAdapter;
    private DataBase db;
    private String theme;
    private ArrayList<NoteModel> newList;
    private Boolean filter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        registerForContextMenu(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(ChooseActivity.this, NoteActivity.class);
                startActivity(ıntent);
            }
        });

        theme = getIntent().getStringExtra("myTag");
        if (theme != null) {
            switch (theme) {
                case "GENEL":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.mavi)));
                    getSupportActionBar().setTitle("Genel");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.mavi)));
                    break;
                case "KİŞİSEL":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.kirmizi)));
                    getSupportActionBar().setTitle("Kişisel");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.kirmizi)));
                    break;
                case "EV":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.sari)));
                    getSupportActionBar().setTitle("Ev");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.sari)));
                    break;
                case "OKUL":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.yesil)));
                    getSupportActionBar().setTitle("Okul");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yesil)));
                    break;
                case "İŞ":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.turkuaz)));
                    getSupportActionBar().setTitle("İş");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.turkuaz)));
                    break;
                case "DİĞER":
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                            .getColor(R.color.turuncu)));
                    getSupportActionBar().setTitle("Diğer");
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.turuncu)));
                    break;
            }
        }

        db = new DataBase(getApplicationContext());
        modelList = new ArrayList<NoteModel>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                newList = new ArrayList<>();
                for (NoteModel noteModel : modelList) {
                    String icerik = noteModel.getContent().toLowerCase();
                    String title = noteModel.getTitle().toLowerCase();
                    if (icerik.contains(newText) || title.contains(newText)) {
                        newList.add(noteModel);
                    }
                }
                noteAdapter.setFilter(newList);
                if (!newList.isEmpty()){
                    filter = true;
                    Listele();
                }


                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.deleteAll) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ChooseActivity.this);
            builder.setTitle("Tüm Kayıtlar Silinecek");
            builder.setMessage("Emin Misiniz ?");
            builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    actionMode.finish();

                }
            });

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id1) {
                    Log.d("EVETTT", "EVETTT");
                    DataBase db = new DataBase(getApplicationContext());
                    db.Sil();
                    Listele();
                    Toast.makeText(getApplicationContext(), "Tüm Kayıtlar Silindi!", Toast.LENGTH_LONG).show();
                }
            });

            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(null);

        Listele();


    }

    public void Listele() {

        if (filter) {
            modelList = newList;
        } else {
            if (theme != null) {
                modelList = db.getTheme(theme);
            } else {
                modelList = db.tumKayitlariGetir();
            }
        }


        noteAdapter = new NoteAdapter(this, modelList, new NoteAdapter.CustomItemClickListener() {

            @Override
            public void onItemClick(int position) {

                NoteModel noteModel = modelList.get(position);
                Intent ıntent = new Intent(ChooseActivity.this, NoteActivity.class);
                ıntent.putExtra("myModel", (Serializable) noteModel);
                startActivity(ıntent);
            }
        }, new NoteAdapter.CustomItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {

                if (actionMode != null) {
                    return;
                }
                MyActionModeCallBack callBack = new MyActionModeCallBack();
                actionMode = startSupportActionMode(callBack);
                recyclerView.getChildAt(position).setSelected(true);
                id = modelList.get(position).getId();

            }

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);

    }

    class MyActionModeCallBack implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.contex_menu, menu);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseActivity.this);
                    builder.setTitle("Kayıt Silinecek");
                    builder.setMessage("Emin Misiniz ?");
                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            actionMode.finish();

                        }
                    });

                    builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id1) {
                            DataBase db = new DataBase(getApplicationContext());
                            db.Sil(id);
                            mode.finish();
                            Listele();
                        }
                    });

                    builder.show();
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