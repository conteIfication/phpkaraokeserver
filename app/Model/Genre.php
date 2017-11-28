<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Genre extends Model
{
    protected $table = 'genre';
    //
    protected $fillable = [
        'name', 'description'
    ];
    public $timestamps = false;
}
