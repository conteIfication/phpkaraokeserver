<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Artist extends Model
{
    //
    protected $fillable = [
        'name', 'artist_type', 'info'
    ];
    public $timestamps = false;
}
