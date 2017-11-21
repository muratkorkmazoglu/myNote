package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class TodoUpdateDialogAdapter extends BaseAdapter {

    private Context context;
    private List<Todo> todoList;
    private LayoutInflater inflater;
    private TextView textview;
    private Button button;
    private View.OnClickListener listener;

    public TodoUpdateDialogAdapter(Context context, List<Todo> todoCountByTag) {
        this.context = context;
        this.todoList = todoCountByTag;
        inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int i) {
        return todoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final TodoDb db = new TodoDb(context);
        view = inflater.inflate(R.layout.todo_update_items, viewGroup, false);

        textview = view.findViewById(R.id.updateText);
        textview.setText(todoList.get(i).getNote().toString());
        button = view.findViewById(R.id.deleteButton);
        button.setOnClickListener(listener);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteToDo(todoList.get(i).getNote());
                todoList.remove(i);
                notifyDataSetChanged();
                
            }
        });

        return view;
    }
}
