<?php

namespace App\Http\Controllers;

use App\Admin;
use Illuminate\Http\Request;

class AdminProfileController extends Controller
{
    //
    function __construct()
    {
        $this->middleware('auth:admin');
    }

    public function profile() {
        $admin = \Auth::guard('admin')->user();
        return view('profile', ['admin' => $admin]);
    }
    public function save(Request $request){
        $admin = Admin::find($request->id);
        $name = $request->name;
        $birthday = $request->birthday;

        if ($name == '' ){
            return 0;
        }

        $admin->name = $name;
        $admin->birthday = $birthday;
        if ($admin->save()){
            return redirect()->back();
        }

        return 0;
    }
}
