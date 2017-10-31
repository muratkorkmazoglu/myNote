package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoListAdapter extends BaseAdapter {

    private Context context;
    private List<TodoModel> modelList;
    private LayoutInflater layoutInflater;
    private TextView listItemName, listItemCount;

    public TodoListAdapter(Context context, List<TodoModel> modelList) {
        this.context = context;
        this.modelList = modelList;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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

        listItemName.setText(modelList.get(i).getListTitle());

        for (int k = 0; k < modelList.size(); k++) {
            if (modelList.get(i).getListTitle().equals(listItemName)){
            }
        }

        listItemCount.setText();
//
//        tv.setText(model.get(i).getText());
//        ımageView.setImageResource(model.get(i).getImage());


        return view;


    }
}
