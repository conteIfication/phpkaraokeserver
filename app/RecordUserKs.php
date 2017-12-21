<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class RecordUserKs extends Model
{
    //
    protected $table = 'record_user_ks';


    public $timestamps = true;
    public function karaokesong() {
        return $this->hasOne('App\KaraokeSong', 'id', 'kar_id');
    }
}
