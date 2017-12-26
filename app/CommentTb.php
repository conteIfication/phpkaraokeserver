<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class CommentTb extends Model
{
    //
    protected $table = 'comment';

    public $timestamps = false;
    public function user() {
        return $this->hasOne('App\User', 'id', 'user_id');
    }
    public function sharedrecord() {
        return $this->hasOne('App\SharedRecording', 'id', 'sr_id');
    }
}
