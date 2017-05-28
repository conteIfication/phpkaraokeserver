<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Database\QueryException;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use League\Flysystem\Exception;

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

    public function logoutSuccess() {
        $data = [
            'tag' => 'logout',
            'success' => true,
            'message' => 'logout success'
        ];
        return json_encode($data);
    }

    public function login(Request $request) {

        $email = $request->get('email');
        $password = $request->get('password');

        $user = User::where('email', $email)->first();

        //NOT FOUND USER
        if (count($user) < 1) {
            return json_encode([ 'tag' => 'login',
                'success' => false,
                'message' => 'not found user'
            ]);
        }

        //Check Match Password
        if ( !Hash::check($password, $user->password) ) {
            $data = [ 'tag' => 'login',
                'success' => false,
                'message' => 'error match password'
            ];
            return json_encode($data);
        }

        $data = [ 'tag' => 'login',
            'success' => true,
            'message' => 'login success'
        ];
        return json_encode($data);
    }

    public function register(Request $request) {
        $name = $request->get("name");
        $email = $request->get("email");
        $password = $request->get("password");

        try {
            User::create([
                'name' => $name,
                'email' => $email,
                'password' => bcrypt($password),
            ]);
        }catch (QueryException $exception) {
            return json_encode(['tag' => 'register', 'success' => false, 'message' => $exception->getMessage()]);
        }

        return json_encode( [ 'tag' => 'register' , 'success' => true, 'message' => "register success"] );
    }
}
