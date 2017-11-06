package com.krkmz.mynote;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class TodoListActivity extends AppCompatActivity {

    private String listTitle;
    private Button saveButton, kaydetButton, iptalButton;
    private EditText listName, listItem;
    private LinearLayout checkBoxContainer;
    private AlertDialog dialog;
    private String checked;
    private CheckBox checkbox;
    private TodoModel todoModel;
    private ListView listView;
    private List<TodoModel> modelList;
    private List<String> modelListJsonFormat;
    private List<String> modelListJsonFormatUpdate;
    private TodoDb db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.todo_list_layout);

        listView = (ListView) findViewById(R.id.listViewTodo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_todo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMyDialog();
            }
        });

        db = new TodoDb(getApplicationContext());
        modelList = new ArrayList<TodoModel>();
        modelListJsonFormat = new ArrayList<String>();
        modelListJsonFormatUpdate = new ArrayList<String>();


    }

    private void showMyDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.todo_dialog, null);

        saveButton = (Button) layout.findViewById(R.id.saveButtonList);
        kaydetButton = (Button) layout.findViewById(R.id.kaydet);
        iptalButton = (Button) layout.findViewById(R.id.iptal);
        listName = (EditText) layout.findViewById(R.id.etTitleList);
        listItem = (EditText) layout.findViewById(R.id.etListItem);
        checkBoxContainer = (LinearLayout) layout.findViewById(R.id.checkboxContainer);

        AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
        builder.setTitle("MyList");
        builder.setView(layout);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!listName.getText().toString().equals("")) {
                    if (!listItem.getText().toString().equals("")) {
                        checkbox = new CheckBox(TodoListActivity.this);
                        checkbox.setText(listItem.getText().toString());

                        if (checkbox.isChecked()) {
                            checked = "true";
                        } else {
                            checked = "false";
                        }
                        checkBoxContainer.addView(checkbox);

                        todoModel = new TodoModel(listItem.getText().toString(), checked);

                        JSONObject json = new JSONObject();
                        try {
                            listTitle = listName.getText().toString();
                            json.put("ItemName", todoModel.getListItemsName());
                            json.put("Checked", todoModel.getChecked());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String arrayList = json.toString();
                        modelListJsonFormat.add(arrayList);


                        listItem.setText("");

                    } else {

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Liste Adı Boş Geçilemez", Toast.LENGTH_LONG).show();
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
            @Override
            public void onClick(View view) {
                TodoDb db = new TodoDb(TodoListActivity.this);

                JSONObject json = new JSONObject();
                for (int i = 0; i < modelListJsonFormat.size(); i++) {
                    try {
                        json.put("Item" + i, modelListJsonFormat.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String arrayList = json.toString();
                System.out.println(arrayList.toString());

                long id = db.kayitEkle(listTitle, arrayList);
                if (id != -1)
                    Toast.makeText(getApplicationContext(), "kaydedildi", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "kaydedilmedi", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                Listele();


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(null);

        Listele();


    }

    private void Listele() {

        modelList = db.tumKayitlariGetir();
        //db.Sil();
        TodoListAdapter adapter = new TodoListAdapter(getApplicationContext(), modelList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                JSONObject json = null;
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View layout = inflater.inflate(R.layout.todo_dialog, null);
                saveButton = (Button) layout.findViewById(R.id.saveButtonList);
                kaydetButton = (Button) layout.findViewById(R.id.kaydet);
                iptalButton = (Button) layout.findViewById(R.id.iptal);
                listName = (EditText) layout.findViewById(R.id.etTitleList);
                listItem = (EditText) layout.findViewById(R.id.etListItem);
                checkBoxContainer = (LinearLayout) layout.findViewById(R.id.checkboxContainer);
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
                builder.setTitle("MyList");
                builder.setView(layout);

                listName.setText(modelList.get(i).getListItemsName().toString());

                try {
                    json = new JSONObject(modelList.get(i).getChecked());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int k = 0; k < json.length(); k++) {
                    try {

                        JSONObject isim = new JSONObject(json.getString("Item" + k));
                        String myIsım = isim.getString("ItemName");
                        String isChecked = isim.getString("Checked");
                        System.out.println(isChecked);
                        checkbox = new CheckBox(TodoListActivity.this);
                        checkbox.setText(myIsım.toString());
                        checkbox.setId(k);
                        checkBoxContainer.addView(checkbox);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                dialog = builder.create();
                dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!listName.getText().toString().equals("")) {
                            if (!listItem.getText().toString().equals("")) {
                                checkbox = new CheckBox(TodoListActivity.this);
                                checkbox.setText(listItem.getText().toString());

                                if (checkbox.isChecked()) {
                                    checked = "true";
                                } else {
                                    checked = "false";
                                }
                                checkBoxContainer.addView(checkbox);

//                                todoModel = new TodoModel(listItem.getText().toString(), checked);
//
//                                JSONObject json = new JSONObject();
//                                try {
//                                    listTitle = listName.getText().toString();
//                                    json.put("ItemName", todoModel.getListItemsName());
//                                    json.put("Checked", todoModel.getChecked());
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                String arrayList = json.toString();
//                                modelListJsonFormat.add(arrayList);


                                listItem.setText("");

                            } else {

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Liste Adı Boş Geçilemez", Toast.LENGTH_LONG).show();
                        }


                    }
                });

            }


        });
    }
}
