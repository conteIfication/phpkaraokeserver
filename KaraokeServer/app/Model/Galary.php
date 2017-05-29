<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Galary extends Model
{
    protected $table = 'galary';
    //
    protected $fillable  = [
        'path', 'up_time'
    ];
    public $timestamps = false;
}
