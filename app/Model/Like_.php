<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Like_ extends Model
{
    //
    protected $table = 'like_';
    protected $fillable = ['uid', 'srid', 'up_time'];
    public $timestamps = false;
}
