package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TodoAdapter extends BaseAdapter {

    private List<Tag> tagList;
    private Context context;
    private LayoutInflater inflater;
    private TextView tagName, tagCount;
    private List<Todo> todoCountByTag;

    public TodoAdapter(Context context, List<Tag> tagList) {
        this.context = context;
        this.tagList = tagList;
        inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public Object getItem(int i) {
        return tagList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.todo_list_items, viewGroup, false);
        tagName = view.findViewById(R.id.tagName);
        tagCount = view.findViewById(R.id.tagCount);

        tagName.setText(tagList.get(i).getTagName().toString());

        TodoDb db = new TodoDb(context);
        todoCountByTag = new ArrayList<Todo>();
        todoCountByTag = db.getAllToDosByTag(tagList.get(i).getTagName().toString());

        tagCount.setText(String.valueOf(todoCountByTag.size()));

        return view;
    }
}
