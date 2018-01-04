<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class AdminProfileController extends Controller
{
    //
    public function profile() {
        return view('profile');
    }
}
