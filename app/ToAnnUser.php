<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ToAnnUser extends Model
{
    //
    protected $table = 'to_ann_user';

    public $timestamps = false;

    public function announcement(){
        return $this->hasOne('App\Announcement', 'id', 'ann_id');
    }
    public function user() {
        return $this->hasOne('App\User', 'id', 'user_id');
    }
}
