package com.krkmz.mynote;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends AppCompatActivity {

    TodoDb db;
    FloatingActionButton fab;
    private Button saveButton, kaydetButton, iptalButton, updateButton;
    private EditText listName, listItem;
    private LinearLayout checkBoxContainer;
    private AlertDialog dialog;
    private ListView listView, todoListview;
    private List<Tag> tagList;
    private View layout;
    private List<Todo> todoCountByTag;
    private LayoutInflater inflater;
    private ActionMode actionMode;
    private Tag dataModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.todo_list_layout);
        db = new TodoDb(getApplicationContext());
        listView = (ListView) findViewById(R.id.listViewTodo);
        fab = (FloatingActionButton) findViewById(R.id.fab_todo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.deleteAll();
                showMyDialog();
            }
        });

        inflater = LayoutInflater.from(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.deleteAllTodo) {


            AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
            builder.setTitle("Tüm Kayıtlar Silinecek");
            builder.setMessage("Emin Misiniz ?");
            builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id1) {
                    Log.d("EVETTT", "EVETTT");

                    db.deleteAll();
                    Listele();
                    Toast.makeText(getApplicationContext(), "Tüm Kayıtlar Silindi!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void showMyDialog() {

        layout = inflater.inflate(R.layout.todo_dialog, null);

        saveButton = (Button) layout.findViewById(R.id.addButton);
        kaydetButton = (Button) layout.findViewById(R.id.kaydet);
        iptalButton = (Button) layout.findViewById(R.id.iptal);
        listName = (EditText) layout.findViewById(R.id.etTitleList);
        listItem = (EditText) layout.findViewById(R.id.etListItem);
        checkBoxContainer = (LinearLayout) layout.findViewById(R.id.checkboxContainer);

        AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
        builder.setTitle("Yapılacaklar Listesi");
        builder.setView(layout);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!listName.getText().toString().equals("")) {
                    if (!listItem.getText().toString().equals("")) {

                        CheckBox checkbox = new CheckBox(TodoActivity.this);
                        checkbox.setText(listItem.getText().toString());
                        checkbox.setChecked(true);
                        checkBoxContainer.addView(checkbox);
                        listItem.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(), "Liste Elemanı Ekleyiniz", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Liste Adı Boş Geçilemez", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iptalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listele();
                dialog.dismiss();

            }

        });

        kaydetButton.setOnClickListener(new View.OnClickListener() {
            CheckBox v;

            @Override
            public void onClick(View view) {

                if (listName.getText().toString().equals("") && checkBoxContainer.getChildCount() == 0) {

                } else {
                    List<Tag> allTags = db.getAllTags();
                    for (Tag tag : allTags) {
                        if (tag.getTagName().equals(listName.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Bu Liste Adı Kullanılıyor. Lütfen Başka Bir Liste Adı Ekleyiniz", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Tag tag1 = new Tag(listName.getText().toString());
                    long tag1_id = db.createTag(tag1);

                    for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
                        v = (CheckBox) checkBoxContainer.getChildAt(i);
                        Todo todo1 = new Todo(v.getText().toString(), 0);
                        long todo1_id = db.createToDo(todo1, new long[]{tag1_id});
                        Listele();
                    }
                    Toast.makeText(getApplicationContext(), "Liste Kaydedildi", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Listele();

    }

    public void Listele() {


        tagList = new ArrayList<Tag>();
        tagList = db.getAllTags();

        TodoAdapter adapter = new TodoAdapter(getApplicationContext(), tagList);
        listView.setAdapter(adapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
        builder.setTitle("Yapılacaklar Listesi");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dataModel = (Tag) adapterView.getItemAtPosition(i);

                layout = inflater.inflate(R.layout.todo_dialog_end, null);
                updateButton = (Button) layout.findViewById(R.id.okButton);
                TextView etTitle = (TextView) layout.findViewById(R.id.etTitle);


                todoListview = (ListView) layout.findViewById(R.id.todoUpdateListview);

                todoCountByTag = new ArrayList<Todo>();
                todoCountByTag = db.getAllToDosByTag(dataModel.getTagName().toString());

                builder.setView(layout);
                dialog = builder.create();

                TodoUpdateDialogAdapter adapter = new TodoUpdateDialogAdapter(getApplicationContext(), todoCountByTag);
                todoListview.setAdapter(adapter);
                etTitle.setText(dataModel.getTagName().toString());


                dialog.show();

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Listele();

                        dialog.dismiss();


                    }
                });


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataModel = (Tag) adapterView.getItemAtPosition(i);
                if (actionMode != null) {
                    //return;
                }
                TodoActivity.ActionModeCallBack callBack = new ActionModeCallBack();
                actionMode = startSupportActionMode(callBack);

                return true;
            }
        });
    }


    class ActionModeCallBack implements ActionMode.Callback {

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
                    Log.d("TAGTAGTAG-----", dataModel.getTagName().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
                    builder.setTitle("Kayıt Silinecek");
                    builder.setMessage("Emin Misiniz ?");
                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            actionMode.finish();

                        }
                    });

                    builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id1) {

                            TodoDb db = new TodoDb(getApplicationContext());
                            db.deleteTag(dataModel, true);

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
