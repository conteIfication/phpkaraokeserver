<?php

namespace App\Http\Controllers;

use App\KaraokeSong;
use App\ReportUserKs;
use App\ReportUserSr;
use App\SharedRecording;
use App\User;
use Illuminate\Http\Request;

class AdminController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth:admin');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $srs = SharedRecording::count();
        $kss = KaraokeSong::count();
        $users = User::count();
        $reportKs = ReportUserKs::count();
        $reportSr = ReportUserSr::count();
        return view('admin', ['srs' => $srs, 'kss' => $kss, 'users' => $users, 'reports' => $reportKs + $reportSr]);
    }
}
