<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class SharedRecordReport extends Model
{
    //
    protected $table = 'shared_record_report';
    protected $fillable = ['rid', 'shared_record_id'];
    public $timestamps = false;
}
