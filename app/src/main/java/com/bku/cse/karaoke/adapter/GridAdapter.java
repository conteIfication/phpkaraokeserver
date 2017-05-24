package com.bku.cse.karaoke.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.model.SmallItem;

import java.util.List;

/**
 * Created by quangthanh on 5/9/2017.
 */

public class GridAdapter extends BaseAdapter {
    private List<SmallItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GridAdapter(Context aContext,  List<SmallItem> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO:
        RecyclerView.ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.fragment_list_item, null);
        TextView countryNameView = (TextView) convertView.findViewById(R.id.textView_countryName);

        ImageView img=(ImageView)convertView.findViewById(R.id.imageView);
        Drawable myDrawable = context.getDrawable(R.drawable.logo);
        img.setImageDrawable(myDrawable);

        SmallItem item = this.listData.get(position);
        countryNameView.setText(item.getSongName());

        return convertView;
    }
}
