<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Like_ extends Model
{
    //
    protected $table = 'like_';
    protected $fillable = ['uid', 'shared_record_id', 'like_time'];
    public $timestamps = false;
}
