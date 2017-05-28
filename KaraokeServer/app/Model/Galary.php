<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Galary extends Model
{
    //
    protected $fillable  = [
        'path', 'up_time'
    ];
    public $timestamps = false;
}
