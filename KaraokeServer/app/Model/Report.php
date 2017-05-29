<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Report extends Model
{
    //
    protected $table = 'report';
    protected $fillable = ['content', 'up_time', 'uid'];
    public $timestamps = false;
}
