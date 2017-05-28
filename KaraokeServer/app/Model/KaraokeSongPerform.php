<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KaraokeSongPerform extends Model
{
    //
    protected $fillable = ['kid', 'artid'];
    public $timestamps = false;
}
