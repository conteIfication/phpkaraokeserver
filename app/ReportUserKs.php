<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ReportUserKs extends Model
{
    //
    protected $table = 'report_user_ks';

    public $timestamps = false;

    public function karaoke() {
        return $this->hasOne('App\KaraokeSong', 'id', 'kar_id');
    }
    public function user() {
        return $this->hasOne('App\User', 'id', 'user_id' );
    }
}
