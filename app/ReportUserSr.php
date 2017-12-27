<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ReportUserSr extends Model
{
    //
    protected $table = 'report_user_sr';

    public $timestamps = false;

    public function user() {
        return $this->hasOne('App\User', 'user_id', 'id');
    }
    public function sharedrecord() {
        return $this->hasOne( 'App\SharedRecording', 'sr_id', 'id');
    }
}
