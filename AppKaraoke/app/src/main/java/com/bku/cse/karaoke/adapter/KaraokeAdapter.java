package com.bku.cse.karaoke.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.controller.SelectModeKaraokeActivity;
import com.bku.cse.karaoke.helper.BaseURLManager;
import com.bku.cse.karaoke.model.KaraokeSong;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class KaraokeAdapter extends RecyclerView.Adapter<KaraokeAdapter.MyViewHolder> {
    public List<KaraokeSong> getKaraokeSongs() {
        return karaokeSongs;
    }

    public void setKaraokeSongs(List<KaraokeSong> karaokeSongs) {
        this.karaokeSongs = karaokeSongs;
    }

    private List<KaraokeSong> karaokeSongs;
    private Context _context;

    public KaraokeAdapter(Context context) {
        this._context = context;
        this.karaokeSongs = new ArrayList<>();
    }

    public KaraokeAdapter(Context context, List<KaraokeSong> karaokeSongs) {
        this.karaokeSongs = karaokeSongs;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final KaraokeSong karaokeSong = karaokeSongs.get(position);
        holder.tv_singer.setText(karaokeSong.getSinger());
        holder.tv_songName.setText(karaokeSong.getName());
        BaseURLManager baseURL = new BaseURLManager(_context);

        //LOAD Image
        Glide.with(_context).load(baseURL.getBaseURL() + karaokeSong.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        //Button Listener
        View.OnClickListener btn_sing_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modeKar_intent = new Intent(_context, SelectModeKaraokeActivity.class);
                modeKar_intent.putExtra("name", karaokeSong.getName());
                modeKar_intent.putExtra("image", karaokeSong.getImage());
                modeKar_intent.putExtra("subtitle_path", karaokeSong.getSubtitle_path());
                modeKar_intent.putExtra("beat_path", karaokeSong.getBeat_path());
                modeKar_intent.putExtra("kid", karaokeSong.getKid());
                modeKar_intent.putExtra("singer", karaokeSong.getSinger());

                _context.startActivity(modeKar_intent);
            }
        };
        holder.btn_sing.setOnClickListener(btn_sing_listener);

    }

    @Override
    public int getItemCount() {
        return karaokeSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_songName, tv_singer;
        public Button btn_sing;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageViewPoster);
            tv_songName = (TextView) view.findViewById(R.id.song_name);
            tv_singer = (TextView) view.findViewById(R.id.singer);
            btn_sing = (Button) view.findViewById(R.id.btn_sing);
        }
    }
}
