<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Admins extends Model
{
    protected $table = 'admins';
    //
    protected $fillable = [
        'username', 'name'
    ];
    public $timestamps = false;
    protected $hidden = [
        'password'
    ];
}
