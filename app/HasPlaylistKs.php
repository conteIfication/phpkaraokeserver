<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class HasPlaylistKs extends Model
{
    //
    protected $table = 'has_playlist_ks';

    public $timestamps = false;

    public function playlist() {
        return $this->hasOne('App\Playlist', 'id', 'pl_id');
    }
    public function karaokesong() {
        return $this->hasOne('App\KaraokeSong', 'id', 'kar_id');
    }
}
