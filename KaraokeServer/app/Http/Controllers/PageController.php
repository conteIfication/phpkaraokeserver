<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PageController extends Controller
{
    function __construct()
    {
        $this->middleware('guest');
    }

    //
    public function show() {
        $galaries = DB::table('galary')->get();

        return view('test', ['galaries' => $galaries]);
    }
}
