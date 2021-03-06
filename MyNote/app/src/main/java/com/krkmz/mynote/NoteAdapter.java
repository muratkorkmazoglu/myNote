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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    public void setFilter(ArrayList<NoteModel> filter) {
        this.filter = filter;
        modelList = new ArrayList<NoteModel>();
        if (this.filter != null) {
            Log.d("LOGGGGGG", "LOGGGGG------");
            modelList.addAll(filter);
            notifyDataSetChanged();
        } else {
            Log.d("ELSE", "ELSEEE------");
            modelList = new ArrayList<NoteModel>();
        }

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
    private ImageView camImage;
    private RelativeLayout rl;
    private ArrayList<NoteModel> filter;
    private NoteModel noteModel;


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
        public TextView title, content, date, themeText;
        public CardView card_view;
        private CircleImageView circleImageView;


        public MyViewHolder(View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.noteItemTitle);
            themeText = (TextView) view.findViewById(R.id.tvTheme);
            content = (TextView) view.findViewById(R.id.noteItemContent);
            date = (TextView) view.findViewById(R.id.noteItemDate);
            camImage = (ImageView) view.findViewById(R.id.camImage);
            rl = (RelativeLayout) view.findViewById(R.id.noteItemRl);
            circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);

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


//        if (this.filter != null) {
//
//            noteModel = modelList.get(position);
//           // notifyDataSetChanged();
//        } else {
//            noteModel = modelList.get(position);
//        }
        noteModel = modelList.get(position);


        holder.title.setText(noteModel.getTitle().toString());
        holder.date.setText(noteModel.getDateTimeFormatted(context));

        switch (noteModel.getTheme()) {
            case "GENEL":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.genel);
                break;
            case "KİŞİSEL":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.kisisel);
                break;
            case "İŞ":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.is);
                break;
            case "OKUL":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.okul);
                break;
            case "EV":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.ev);
                break;
            case "DİĞER":
                holder.themeText.setText(noteModel.getTheme().toString());
                holder.circleImageView.setImageResource(R.mipmap.diger);
                break;
        }


        if (noteModel.getContent().length() > 150) {
            holder.content.setText(noteModel.getContent().substring(0, 150));
        } else {
            holder.content.setText(noteModel.getContent().toString());
        }

        if (noteModel.getDirectory() != null) {
            camImage.setImageResource(R.mipmap.cam);
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
