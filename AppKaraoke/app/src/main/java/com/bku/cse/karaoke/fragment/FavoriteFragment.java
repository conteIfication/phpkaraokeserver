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
import android.widget.TextView;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.adapter.KaraokeAdapter;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.model.FavoriteSong;
import com.bku.cse.karaoke.model.KSongList;
import com.bku.cse.karaoke.model.KaraokeSong;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thonghuynh on 6/6/2017.
 */

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    KaraokeAdapter mAdapter;
    DatabaseHelper db;
    TextView tv_num_song;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        //Init
        tv_num_song = (TextView) view.findViewById(R.id.fs_num_records) ;
        db = new DatabaseHelper(getContext());
        List<FavoriteSong> listFS = null;
        final List<KaraokeSong> listKS = new ArrayList<>();
        mAdapter = new KaraokeAdapter(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.myfavorite_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        //get List FavoriteSong
        listFS= db.get_AllFavoriteSong();

        //service
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        for ( FavoriteSong fs : listFS ) {
            Call<KaraokeSong> call = apiService.getKaraokeSong( fs.getKid() );
            call.enqueue(new Callback<KaraokeSong>() {
                @Override
                public void onResponse(Call<KaraokeSong> call, Response<KaraokeSong> response) {
                    if (response.isSuccessful()) {
                        listKS.add( response.body() );
                        mAdapter.setKaraokeSongs(listKS);
                        mAdapter.notifyDataSetChanged();

                        tv_num_song.setText( listKS.size() + " Songs" );
                    }
                }

                @Override
                public void onFailure(Call<KaraokeSong> call, Throwable t) {
                    Log.d("err", "fail get karaoke list");
                }
            });
        }


        return view;
    }
}
