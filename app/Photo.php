<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Photo extends Model
{
    //
    protected $table = 'photo';

    public $timestamps = false;

    public function user(){
        return $this->hasOne('App\User', 'id', 'user_id');
    }
}
