<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class KSBelong extends Model
{
    protected $table = 'ks_belong';
    //
    protected $fillable = ['kid', 'genid'];
    public $timestamps = false;
}
