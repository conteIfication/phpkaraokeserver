<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;

class AdminLoginController extends Controller
{
    //
    public function __construct(){
        $this->middleware('guest:admin', ['except' => ['logout']]);
    }

    public function showLoginForm(){
        return view('auth.admin-login');
    }
    public function login(Request $request){
        //validate the form data
        $this->validate($request, [
            'email' => 'required|email',
            'password' => 'required|min:4'
        ]);
        //attempt to log the user in
        if (Auth::guard('admin')->attempt(['email' => $request->email, 'password' => $request->password], $request->remember)){
            //if successful
            return redirect()->route('admin.dashboard');
        }
        //if unsuccessful
        return redirect()->back()->withInput($request->only('email', 'remember'));
    }
    public function logout() {
        Auth::guard('admin')->logout();
        return redirect()->route('admin.login');
    }
}
