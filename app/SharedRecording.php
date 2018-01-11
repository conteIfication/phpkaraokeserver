<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class SharedRecording extends Model
{
    //
    protected $table = 'sharedrecord';
    public $timestamps = false;

    protected $fillable = [
        'view_no', 'score', 'content'
    ];

    public function karaoke() {
        return $this->hasOne('App\KaraokeSong', 'id', 'kar_id');
    }
    public function user() {
        return $this->hasOne('App\User', 'id', 'user_id' );
    }
}
