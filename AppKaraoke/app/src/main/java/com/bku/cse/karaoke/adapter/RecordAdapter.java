package com.bku.cse.karaoke.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.controller.DetailActivity;
import com.bku.cse.karaoke.controller.PlayMusicActivity;
import com.bku.cse.karaoke.controller.SelectModeKaraokeActivity;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.KaraokeSong;
import com.bku.cse.karaoke.model.RecordedSong;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thonghuynh on 6/9/2017.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private List<RecordedSong> recordedSongs;
    private Context _context;

    public RecordAdapter(Context context) {
        this._context = context;
        this.recordedSongs = new ArrayList<>();
    }

    public RecordAdapter(Context context, List<RecordedSong> recordedSongs) {
        this.recordedSongs = recordedSongs;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RecordedSong recordedSong = recordedSongs.get(position);
        SessionManager session = new SessionManager(_context);

        //get KaraokeSong
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<KaraokeSong> call = apiService.getKaraokeSong( recordedSong.getKid() );
        call.enqueue(new Callback<KaraokeSong>() {
            @Override
            public void onResponse(Call<KaraokeSong> call, Response<KaraokeSong> response) {
                if (response.isSuccessful()) {
                    //
                    holder.tv_songName.setText( response.body().getName() );
                }
                else {
                    Log.d("GetError", "get method fail");
                }
            }

            @Override
            public void onFailure(Call<KaraokeSong> call, Throwable t) {
                Log.d("GetError", "get karaoke song info error");
            }
        });

        //holder.tv_songName.setText( recordedSong.getPath() );
        holder.tv_up_time.setText( recordedSong.getUp_time() );
        holder.tv_owner.setText( session.getUserInfo().getName() );

        //if share
        if ( recordedSong.getIs_shared() ) {
            holder.tv_share.setVisibility(View.VISIBLE);
            holder.icon_share.setVisibility(View.VISIBLE);
        }
        else {
            holder.tv_share.setVisibility(View.INVISIBLE);
            holder.icon_share.setVisibility(View.INVISIBLE);
        }

        //set btn_more listener
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(_context, v);
                popupMenu.getMenuInflater().inflate( R.menu.popup_record_setting, popupMenu.getMenu() );

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.record_setting_delete:
                                //delete record
                                DatabaseHelper db = new DatabaseHelper(_context);
                                db.delete_RecordedSong( recordedSong.getRsid() );
                                deleteFile_(recordedSong.getPath());
                                recordedSongs.remove(position);
                                notifyDataSetChanged();
                                db.closeDB();
                                Toast.makeText(_context, "Delete", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.record_setting_share:
                                Toast.makeText(_context, "Delete", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.record_setting_play:
                                Intent intent = new Intent(_context, PlayMusicActivity.class);
                                intent.putExtra("path", recordedSong.getPath());
                                _context.startActivity( intent );
                                break;
                            default:break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }
    public boolean deleteFile_(String path) {
        File file = new File(path);
        if (file.exists())
            if (! file.delete())
                return false;
        return true;
    }

    @Override
    public int getItemCount() {
        return recordedSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView image_cover, icon_share;
        public TextView tv_songName, tv_owner, tv_up_time, tv_share;
        public ImageButton btn_more;

        public MyViewHolder(View view) {
            super(view);
            image_cover = (ImageView) view.findViewById(R.id.record_image_cover);
            icon_share = (ImageView) view.findViewById(R.id.record_share_icon);
            tv_songName = (TextView) view.findViewById(R.id.record_songname);
            tv_owner = (TextView) view.findViewById(R.id.record_owner);
            tv_up_time = (TextView) view.findViewById(R.id.record_up_time);
            tv_share = (TextView) view.findViewById(R.id.record_share_lb);
            btn_more = (ImageButton) view.findViewById(R.id.record_btn_more);

        }
    }

}
