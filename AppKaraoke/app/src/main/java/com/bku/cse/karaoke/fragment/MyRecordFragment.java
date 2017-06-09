package com.bku.cse.karaoke.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.adapter.KaraokeAdapter;
import com.bku.cse.karaoke.adapter.RecordAdapter;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.model.KSongList;
import com.bku.cse.karaoke.model.RecordedSong;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thonghuynh on 6/6/2017.
 */

public class MyRecordFragment extends Fragment {
    RecyclerView recyclerView;
    RecordAdapter mAdapter;
    List<RecordedSong> mList;
    DatabaseHelper db;
    TextView tv_number_record;
    Context _ApplicationContext;

    public MyRecordFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myrecord, container, false);

        //Init
        db = new DatabaseHelper(getContext());
        tv_number_record = (TextView) view.findViewById(R.id.mr_num_records);


        //Get data from Client RecordedSong List
        mList = db.get_AllRecordedSong();

        mAdapter = new RecordAdapter( getContext() , mList);
        recyclerView = (RecyclerView) view.findViewById(R.id.myrecord_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        //set Number Record
        tv_number_record.setText( mList.size() + " Records" );

        return view;
    }
}
