<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KaraokeSong extends Model
{
    protected $table = 'karaoke_song';
    //
    protected $fillable = [
        'name', 'subtitle_path', 'beat_path', 'view_no',
        'up_time', 'image', 'uid'
    ];

    public $timestamps  = false;
}
