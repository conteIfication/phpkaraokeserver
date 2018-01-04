<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PerformKsArtist extends Model
{
    //
    protected $table = 'perform_ks_artist';

    public $timestamps = false;

    public function artist() {
        return $this->hasOne('App\Artist', 'art_id', 'id');
    }
    public function karaokesong(){
        return $this->hasOne('App\KaraokeSong', 'kar_id', 'id');
    }
}
