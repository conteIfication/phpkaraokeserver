<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Playlist extends Model
{
    //
    protected $table = 'playlist';

    public $timestamps = false;
    public function user() {
        return $this->hasOne('Api\User', 'user_id', 'id');
    }
}
