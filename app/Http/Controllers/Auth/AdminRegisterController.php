<?php

namespace App\Http\Controllers\Auth;

use App\Admin;
use App\User;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class AdminRegisterController extends Controller
{
    public function __construct()
    {
        $this->middleware('guest:admin');
    }

    public function showRegistrationForm(){
        return view('auth.admin-register');
    }
    public function register(Request $request) {
        $this->validate($request, [
            'name' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:admin',
            'password' => 'required|string|min:6|confirmed',
        ]);
        $admin = $this->create($request->all());
        Auth::guard('admin')->login($admin);
        return redirect()->route('admin.dashboard');
    }

    protected function create(array $data)
    {
        return Admin::create([
            'name' => $data['name'],
            'email' => $data['email'],
            'password' => bcrypt($data['password']),
        ]);
    }
}
