<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Relation extends Model
{
    //
    protected $table = 'relation';

    public $timestamps = false;

    public function user() {
        return $this->hasOne( 'App\User', 'id', 'user_id' );
    }

    public function other_user() {
        return $this->hasOne( 'App\User', 'id', 'other_id' );
    }
}
