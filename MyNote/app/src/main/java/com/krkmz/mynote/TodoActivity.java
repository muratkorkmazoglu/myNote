package com.krkmz.mynote;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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


//        // Creating tags
//        Tag tag1 = new Tag("Shopping");
//        Tag tag2 = new Tag("Important");
//        Tag tag3 = new Tag("Watchlist");
//        Tag tag4 = new Tag("Androidhive");
//
//        // Inserting tags in db
//        long tag1_id = db.createTag(tag1);
//        Log.d("Tag ID", "Tag ID: " + tag1_id);
//        Log.d("Tag ID", "Tag ID: " + tag1.getId());
//        long tag2_id = db.createTag(tag2);
//        long tag3_id = db.createTag(tag3);
//        long tag4_id = db.createTag(tag4);
//
//        Log.d("Tag Count", "Tag Count: " + db.getAllTags().size());
//
//        // Creating ToDos
//        Todo todo1 = new Todo("iPhone 5S", 0);
//        Todo todo2 = new Todo("Galaxy Note II", 0);
//        Todo todo3 = new Todo("Whiteboard", 0);
//
//        Todo todo4 = new Todo("Riddick", 0);
//        Todo todo5 = new Todo("Prisoners", 0);
//        Todo todo6 = new Todo("The Croods", 0);
//        Todo todo7 = new Todo("Insidious: Chapter 2", 0);
//
//        Todo todo8 = new Todo("Don't forget to call MOM", 0);
//        Todo todo9 = new Todo("Collect money from John", 0);
//
//        Todo todo10 = new Todo("Post new Article", 0);
//        Todo todo11 = new Todo("Take database backup", 0);
//
//        // Inserting todos in db
//        // Inserting todos under "Shopping" Tag
//        long todo1_id = db.createToDo(todo1, new long[]{tag1_id});
//        long todo2_id = db.createToDo(todo2, new long[]{tag1_id});
//        long todo3_id = db.createToDo(todo3, new long[]{tag1_id});
//
//        // Inserting todos under "Watchlist" Tag
//        long todo4_id = db.createToDo(todo4, new long[]{tag3_id});
//        long todo5_id = db.createToDo(todo5, new long[]{tag3_id});
//        long todo6_id = db.createToDo(todo6, new long[]{tag3_id});
//        long todo7_id = db.createToDo(todo7, new long[]{tag3_id});
//
//        // Inserting todos under "Important" Tag
//        long todo8_id = db.createToDo(todo8, new long[]{tag2_id});
//        long todo9_id = db.createToDo(todo9, new long[]{tag2_id});
//
//        // Inserting todos under "Androidhive" Tag
//        long todo10_id = db.createToDo(todo10, new long[]{tag4_id});
//        long todo11_id = db.createToDo(todo11, new long[]{tag4_id});
//
//        Log.e("Todo Count", "Todo count: " + db.getToDoCount());
//
//        // "Post new Article" - assigning this under "Important" Tag
//        // Now this will have - "Androidhive" and "Important" Tags
//        db.createTodoTag(todo10_id, tag2_id);
//
//        // Getting all tag names
//        Log.d("Get Tags", "Getting All Tags");
//
//        List<Tag> allTags = db.getAllTags();
//        for (Tag tag : allTags) {
//            Log.d("Tag Name", tag.getTagName());
//        }
//
//        // Getting all Todos
//        Log.d("Get Todos", "Getting All ToDos");
//
//        List<Todo> allToDos = db.getAllToDos();
//        for (Todo todo : allToDos) {
//            Log.d("ToDo", todo.getNote());
//        }
//
//        // Getting todos under "Watchlist" tag name
//        Log.d("ToDo", "Get todos under single Tag name");
//
//        List<Todo> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
//        for (Todo todo : tagsWatchList) {
//            Log.d("ToDo Watchlist", todo.getNote());
//        }
//
//        // Deleting a ToDo
//        Log.d("Delete ToDo", "Deleting a Todo");
//        Log.d("Tag Count", "Tag Count Before Deleting: " + db.getToDoCount());
//
//        db.deleteToDo(todo8_id);
//
//        Log.d("Tag Count", "Tag Count After Deleting: " + db.getToDoCount());
//
//        // Deleting all Todos under "Shopping" tag
//        Log.d("Tag Count",
//                "Tag Count Before Deleting 'Shopping' Todos: "
//                        + db.getToDoCount());
//
//        db.deleteTag(tag1, true);
//
//        Log.d("Tag Count",
//                "Tag Count After Deleting 'Shopping' Todos: "
//                        + db.getToDoCount());
//
//        // Updating tag name
//        tag3.setTagName("Movies to watch");
//        db.updateTag(tag3);
//
//        // Don't forget to close database connection
//        db.closeDB();
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
        builder.setTitle("MyList");
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
                dialog.dismiss();

            }

        });

        kaydetButton.setOnClickListener(new View.OnClickListener() {
            CheckBox v;

            @Override
            public void onClick(View view) {

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
        builder.setTitle("MyList");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Tag dataModel = (Tag) adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), dataModel.getTagName().toString(), Toast.LENGTH_SHORT).show();

                layout = inflater.inflate(R.layout.todo_update_dialog, null);
                saveButton = (Button) layout.findViewById(R.id.addButton);
                updateButton = (Button) layout.findViewById(R.id.updateButton);
                iptalButton = (Button) layout.findViewById(R.id.iptal);
                listName = (EditText) layout.findViewById(R.id.etTitleList);
                listItem = (EditText) layout.findViewById(R.id.etListItem);
                todoListview = (ListView) layout.findViewById(R.id.todoUpdateListview);

                todoCountByTag = new ArrayList<Todo>();
                todoCountByTag = db.getAllToDosByTag(dataModel.getTagName().toString());

                builder.setView(layout);
                dialog = builder.create();

                TodoUpdateDialogAdapter adapter = new TodoUpdateDialogAdapter(getApplicationContext(), todoCountByTag);
                todoListview.setAdapter(adapter);
                listName.setText(dataModel.getTagName().toString());

                dialog.show();

                iptalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {dialog.dismiss();}});


            }
        });
    }
}
