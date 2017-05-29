<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KaraokeSongBelongIn extends Model
{
    protected $table = 'karaoke_song_belong_in';
    //
    protected $fillable = ['kid', 'genid'];
    public $timestamps = false;
}
