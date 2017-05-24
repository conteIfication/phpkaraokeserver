package com.bku.cse.karaoke.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bku.cse.karaoke.controller.Detail;
import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.adapter.GridAdapter;
import com.bku.cse.karaoke.model.SmallItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangthanh on 5/9/2017.
 */

public class HotFragment extends Fragment {
    public HotFragment() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hot, container, false);
        //TODO:
        List<SmallItem> data = getListData();
        final GridView gridView = (GridView) view.findViewById(R.id.gridView_hot);
        gridView.setAdapter(new GridAdapter(view.getContext(), data));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            gridView.setNestedScrollingEnabled(true);
        }

        // Khi người dùng click vào các GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                SmallItem country = (SmallItem) o;
                Toast.makeText(view.getContext(), "Selected :"
                        + " " + country.getSongName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("TITLE", country.getSongName());
                startActivity(intent);
            }
        });

        return view;
    }
    private  List<SmallItem> getListData() {
        List<SmallItem> list = new ArrayList<SmallItem>();
        SmallItem vietnam = new SmallItem("Vietnam");
        SmallItem usa = new SmallItem("United States");
        SmallItem russia = new SmallItem("Russia");
        SmallItem australia = new SmallItem("Australia");
        SmallItem japan = new SmallItem("Japan");

        list.add(vietnam);
        list.add(usa);
        list.add(russia);
        list.add(australia);
        list.add(japan);
        list.add(vietnam);
        list.add(usa);
        list.add(russia);
        list.add(australia);
        list.add(japan);

        return list;
    }
}
