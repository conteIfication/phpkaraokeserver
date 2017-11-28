<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KSPerform extends Model
{
    //
    protected $table = 'ks_perform';
    protected $fillable = ['kid', 'artid'];
    public $timestamps = false;
}
