package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muratkorkmazoglu on 24.10.2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<ListModel> model;
    private Context context;
    private ImageView ımageView;
    private TextView tv;
    LayoutInflater inflater;

    public CustomListAdapter(Context context, ArrayList<ListModel> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.list_item, viewGroup, false);
        tv=view.findViewById(R.id.tvItem);
        ımageView=view.findViewById(R.id.imageItem);

        tv.setText(model.get(i).getText());
        ımageView.setImageResource(model.get(i).getImage());


        return view;
    }
}
