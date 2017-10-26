package com.krkmz.mynote;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


public class TagListAdapter extends BaseAdapter {
    private Context context;
    private List<NoteModel> modelList;

    private int genel=0, kisisel=0, is=0, okul=0, ev=0, diger=0;
    private LayoutInflater inflater;
    private TextView tvTagName, tvTagCount;
    private CircleImageView tagImageView;

    public TagListAdapter(Context context, List<NoteModel> modelList) {
        this.context = context;
        this.modelList = modelList;
        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        for (int k = 0; k < modelList.size(); k++) {
            if (modelList.get(k).getTheme().equals("GENEL")) {
                genel++;
                Log.d("GENEL",String.valueOf(genel));
            }
            else if (modelList.get(k).getTheme().equals("KİŞİSEL")) {
                kisisel++;
                Log.d("KİŞİSEL",String.valueOf(kisisel));
            }
            else if (modelList.get(k).getTheme().equals("İŞ")) {
                is++;
                Log.d("İŞ",String.valueOf(is));
            }
            else if (modelList.get(k).getTheme().equals("EV")) {
                ev++;
                Log.d("EV",String.valueOf(ev));
            }
            else if (modelList.get(k).getTheme().equals("OKUL")) {
                okul++;
                Log.d("OKUL",String.valueOf(okul));
            }
            else if (modelList.get(k).getTheme().equals("DİĞER")) {
                diger++;
                Log.d("DİĞER",String.valueOf(diger));
            }
        }


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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.tag_item, viewGroup, false);
        tvTagName = view.findViewById(R.id.tvThemeName);
        tvTagCount = view.findViewById(R.id.tvThemeCount);
        tagImageView = view.findViewById(R.id.tagImageView);



        if (modelList.get(i).getTheme().equals("GENEL")) {
            tagImageView.setImageResource(R.mipmap.genel);
            tvTagName.setText("Genel");
            tvTagCount.setText(String.valueOf(genel));
        }
        if (modelList.get(i).getTheme().equals("KİŞİSEL")) {
            tagImageView.setImageResource(R.mipmap.kisisel);
            tvTagName.setText("Kişisel");
            tvTagCount.setText(String.valueOf(kisisel));
        }
        if (modelList.get(i).getTheme().equals("İŞ")) {
            tagImageView.setImageResource(R.mipmap.is);
            tvTagName.setText("İş");
            tvTagCount.setText(String.valueOf(is));
        }
        if (modelList.get(i).getTheme().equals("EV")) {
            tagImageView.setImageResource(R.mipmap.ev);
            tvTagName.setText("Ev");
            tvTagCount.setText(String.valueOf(this.ev));
        }
        if (modelList.get(i).getTheme().equals("OKUL")) {
            tagImageView.setImageResource(R.mipmap.okul);
            tvTagName.setText("Okul");
            tvTagCount.setText(String.valueOf(okul));
        }
        if (modelList.get(i).getTheme().equals("DİĞER")) {
            tagImageView.setImageResource(R.mipmap.diger);
            tvTagName.setText("Diğer");
            tvTagCount.setText(String.valueOf(diger));
        }

        return view;
    }

}
