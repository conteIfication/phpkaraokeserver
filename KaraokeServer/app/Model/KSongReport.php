<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KSongReport extends Model
{
    //
    protected $fillable = ['rid', 'ksong_id'];
    public $timestamps = false;
}