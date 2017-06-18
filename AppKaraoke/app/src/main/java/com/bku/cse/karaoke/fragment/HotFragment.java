package com.bku.cse.karaoke.fragment;

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

import com.bku.cse.karaoke.adapter.KaraokeAdapter;
import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.model.KSongList;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quangthanh on 5/9/2017.
 */

public class HotFragment extends Fragment {
    public HotFragment() {}
    private static final String TAG = HotFragment.class.getSimpleName();
    RecyclerView recyclerView;
    KaraokeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hot, container, false);

        mAdapter = new KaraokeAdapter(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.fm_hot_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        //Get data from Server Karaoke Song List
        ApiInterface apiService = ApiClient.getClient( getContext() ).create(ApiInterface.class);
        Call<KSongList> call = apiService.getHotSong();
        call.enqueue(new Callback<KSongList>() {
            @Override
            public void onResponse(Call<KSongList> call, Response<KSongList> response) {
                if (response.isSuccessful()) {
                    mAdapter.setKaraokeSongs( response.body().getListKSongs() );
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<KSongList> call, Throwable t) {
                // TODO: error
                Log.e("Data return", "NOT format allow");
            }
        });


        return view;
    }

}
