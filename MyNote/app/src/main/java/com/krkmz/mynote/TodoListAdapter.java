package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TodoListAdapter extends BaseAdapter {

    private Context context;
    private List<TodoModel> modelList;
    private LayoutInflater layoutInflater;
    private TextView listItemName, listItemCount;
    private String listNameSon;
    int sayac = 0;

    public TodoListAdapter(Context context, List<TodoModel> modelList) {
        this.context = context;
        this.modelList = modelList;
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return modelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        JSONObject json = null;
        view = layoutInflater.inflate(R.layout.todo_listview_item, viewGroup, false);

        listItemName = view.findViewById(R.id.listname);
        listItemCount = view.findViewById(R.id.listcount);

        listItemName.setText(modelList.get(i).getListItemsName());
        try {
            json = new JSONObject(modelList.get(i).getChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listItemCount.setText(String.valueOf(json.length()));
//
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//




        return view;


    }
}
