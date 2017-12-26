<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class LikeUserSr extends Model
{
    //
    protected $table = 'like_user_sr';

    public $timestamps = false;

    public function user() {
        return $this->hasOne('App\User', 'id', 'user_id');
    }
    public function sharedrecord() {
        return $this->hasOne('App\SharedRecording', 'id', 'sr_id');
    }

}
