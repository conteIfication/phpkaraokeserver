<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class SharedRecord extends Model
{
    //
    protected $table = 'shared_record';
    protected $fillable = ['uid', 'content', 'record_type', 'ksong_id',
        'share_time'
    ];
    public $timestamps = false;
}
