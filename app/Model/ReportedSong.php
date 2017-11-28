<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Report extends Model
{
    //
    protected $table = 'reported_song';
    protected $fillable = ['content', 'up_time', 'uid', 'is_read',
        'kid', 'srid', 'kflag', 'srflag'];
    public $timestamps = false;
}
