<?php

namespace App\Http\Controllers\Api\Auth;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Laravel\Passport\Client;

class LoginController extends Controller
{
    private $client;

    public function __construct()
    {
        $this->client = Client::find(1);
    }

    public function login(Request $request) {

        $this->validate($request, [
            'email' => 'required|email',
            'password' => 'required|max:100|min:4'
        ]);

        $params = [
            'grant_type' => 'password',
            'client_id' => $this->client->id,
            'client_secret' => $this->client->secret,
            'username' => request('email'),
            'password' => request('password'),
            'scope' => '*',
        ];
        $request->request->add($params);
        $proxy = Request::create('/oauth/token', 'POST');

        return \Route::dispatch($proxy);
    }
    public function refresh(Request $request) {
        $this->validate($request, [
            'refresh_token' => 'required'
        ]);

        $param = [
            'grant_type' => 'refresh_token',
            'refresh_token' => request('refresh_token'),
            'client_id' => $this->client->id,
            'client_secret' => $this->client->secret,
            'scope' => '',
        ];

        $request->request->add($param);
        $proxy = Request::create('/oauth/token', 'POST');

        return \Route::dispatch($proxy);
    }
    public function logout(Request $request) {
        if (\Auth::check()) {
            $accessToken = \Auth::user()->token();
            $accessToken->revoke();
        }
        return response()->json([], 204);
    }
}
