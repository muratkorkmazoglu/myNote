package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.finishOnCloseSystemDialogs;

public class TodoListAdapter extends BaseAdapter {

    private Context context;
    private List<HashMap<String,String>> modelList;
    private LayoutInflater layoutInflater;
    private TextView listItemName, listItemCount;
    private String listNameSon;
    int sayac = 0;

    public TodoListAdapter(Context context, List<HashMap<String,String>> modelList) {
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

        view = layoutInflater.inflate(R.layout.todo_listview_item, viewGroup, false);

        listItemName = view.findViewById(R.id.listname);
        listItemCount = view.findViewById(R.id.listcount);
        HashMap<String,String> listName = modelList.get(i);

        listItemName.setText(listName.get("titleName"));

        try {

            JSONObject jsnobject = new JSONObject(listName.get("Item"));

        JSONArray jsonArray = jsnobject.getJSONArray(listName.get("Item"));
        for (int k = 0; k < jsonArray.length(); k++) {
            JSONObject explrObject = jsonArray.getJSONObject(k);
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        Container c = gson.fromJson(listName.get("Item"), Container.class);
        for (TodoModel r : c.toModel)
            System.out.println(r);

       //String deneme =  new Gson().fromJson(listName.get("Item"),String.class);

       System.out.println(listName.get("Item"));


        //listItemName.setText(modelList.get(i).getListTitle());
      //  Log.d("SİZE", String.valueOf(modelList.size()));

//        for (int k = 0; k < modelList.size(); k++) {
//
//            if (modelList.get(k).getListTitle().toString().equals(modelList.get(i).getListTitle().toString())) {
//                sayac++;
//            }
//            if (listName.equals(modelList.get(k).getListTitle())) {
//                listName = modelList.get(k).getListTitle();
//
//            } else {
//                listNameSon = listName;
//
//                listName = null;
//            }
//        }
        //listItemName.setText(listName.toString());


        sayac = 0;

        return view;


    }
}
class Container {
    List<TodoModel> toModel;
}