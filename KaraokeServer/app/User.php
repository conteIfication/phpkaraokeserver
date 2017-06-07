<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Model
{
    protected $table = 'user';
    protected $fillable = [
        'name', 'email', 'phone_no', 'gender', 'birthday', 'avatar'
    ];
    public $timestamps = false;
    protected $hidden = [
      'password'
    ];
}
