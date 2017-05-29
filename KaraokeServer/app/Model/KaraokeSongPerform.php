<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KaraokeSongPerform extends Model
{
    //
    protected $table = 'karaoke_song_perform';
    protected $fillable = ['kid', 'artid'];
    public $timestamps = false;
}
