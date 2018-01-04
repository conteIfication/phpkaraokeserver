<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class Admin extends Authenticatable
{
    use Notifiable;

    protected $guarded = 'admin';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $table = 'admin';
    protected $fillable = [
        'name', 'email', 'password','birthday','role'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

}
