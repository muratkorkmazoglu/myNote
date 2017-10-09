package com.krkmz.mynote;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    public void setFilter(ArrayList<NoteModel> filter) {
        modelList=new ArrayList<>();
        modelList.addAll(filter);
        notifyDataSetChanged();
    }

    public interface CustomItemClickListener {
        void onItemClick(int position);
    }

    public interface CustomItemLongClickListener {
        void onItemLongClick(int position);
    }

    private Context context;
    private List<NoteModel> modelList;
    private CustomItemClickListener listener;
    private CustomItemLongClickListener longClickListener;


    public NoteAdapter(Context context, List<NoteModel> modelList, CustomItemClickListener listener, CustomItemLongClickListener longClickListener) {
        this.context = context;
        this.modelList = modelList;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    public NoteAdapter(Context context, List<NoteModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content, date;
        public CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.noteItemTitle);
            content = (TextView) view.findViewById(R.id.noteItemContent);
            date = (TextView) view.findViewById(R.id.noteItemDate);

        }
    }


    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);

        final MyViewHolder view_holder = new NoteAdapter.MyViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(view_holder.getPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickListener.onItemLongClick(view_holder.getPosition());
                return true;
            }
        });

        return view_holder;

      
    }

    @Override
    public void onBindViewHolder(NoteAdapter.MyViewHolder holder, int position) {

        NoteModel noteModel = modelList.get(position);
        holder.title.setText(noteModel.getTitle().toString());
        holder.date.setText(noteModel.getDateTimeFormatted(context));
        if (noteModel.getContent().length() > 150) {
            holder.content.setText(noteModel.getContent().substring(0, 150));
        } else {
            holder.content.setText(noteModel.getContent().toString());
        }
        if (modelList.get(position).getImage()!=null){
            Log.d("IMAGE","IMAGE");
        }


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
