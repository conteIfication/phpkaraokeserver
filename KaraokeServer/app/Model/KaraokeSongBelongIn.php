<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KaraokeSongBelongIn extends Model
{
    //
    protected $fillable = ['kid', 'genid'];
    public $timestamps = false;
}
