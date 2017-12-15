<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class KaraokeSong extends Model
{
    //

    protected $table = 'karaokesong';
    protected $fillable = [

    ];
    public $timestamps = false;

    public function genres() {
        return $this->belongsToMany('App\Genre', 'belong_ks_genre', 'kar_id', 'genre_id');
    }
    public function artists() {
        return $this->belongsToMany('App\Artist', 'perform_ks_artist', 'kar_id', 'art_id');
    }
}
