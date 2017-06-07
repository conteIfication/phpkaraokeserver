<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class SharedRecord extends Model
{
    //
    protected $table = 'shared_record';
    protected $fillable = [ 'content', 'record_type', 'uid', 'score', 'path',
        'kid', 'up_time'
    ];
    public $timestamps = false;
}
