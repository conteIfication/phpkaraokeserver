<?php

namespace App\Http\Controllers;

use App\Model\Artist;
use App\Model\KaraokeSong;
use App\Model\KaraokeSongPerform;
use App\Model\KSPerform;
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
        $user->gender = ($user->gender == 1) ? true : false;

        //NOT FOUND USER
        if (count($user) < 1) {
            return json_encode([ 'tag' => 'login',
                'success' => false,
                'message' => 'not found user',
                'user' => null
            ]);
        }

        //Check Match Password
        if ( !Hash::check($password, $user->password) ) {
            $data = [ 'tag' => 'login',
                'success' => false,
                'message' => 'error match password',
                'user' => null
            ];
            return json_encode($data);
        }

        $data = [ 'tag' => 'login',
            'success' => true,
            'message' => 'login success',
            'user' => $user
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

    public function getAllSong() {
        $allsongs = KaraokeSong::all();

        foreach ($allsongs as $song) {
            $arr_singer_name = KSPerform::where('kid', $song->kid)
                ->join('artist', 'artist.artid', 'ks_perform.artid')
                ->select('artist.*')
                ->pluck('artist.name')
            ;
            $name_of_singer = '';
            foreach ($arr_singer_name as $singer_name)
                if ($name_of_singer != '')
                    $name_of_singer .= (' - ' .  $singer_name);
                else
                    $name_of_singer .= $singer_name;

            $song->singer = $name_of_singer;
        }

        return json_encode(['listKSongs' => $allsongs]);
    }

    public function getHotSong() {
        $hostsongs = KaraokeSong::orderBy("view_no", "dsc")->get();

        foreach ($hostsongs as $song) {
            $arr_singer_name = KSPerform::where('kid', $song->kid)
                ->join('artist', 'artist.artid', 'ks_perform.artid')
                ->select('artist.*')
                ->pluck('artist.name')
            ;
            $name_of_singer = '';
            foreach ($arr_singer_name as $singer_name)
                if ($name_of_singer != '')
                    $name_of_singer .= (' - ' .  $singer_name);
                else
                    $name_of_singer .= $singer_name;

            $song->singer = $name_of_singer;
        }

        return json_encode(['listKSongs' => $hostsongs]);
    }
    public function getNewSong() {
        $hostsongs = KaraokeSong::orderBy("up_time", "dsc")->get();

        foreach ($hostsongs as $song) {
            $arr_singer_name = KSPerform::where('kid', $song->kid)
                ->join('artist', 'artist.artid', 'ks_perform.artid')
                ->select('artist.*')
                ->pluck('artist.name')
            ;
            $name_of_singer = '';
            foreach ($arr_singer_name as $singer_name)
                if ($name_of_singer != '')
                    $name_of_singer .= (' - ' .  $singer_name);
                else
                    $name_of_singer .= $singer_name;

            $song->singer = $name_of_singer;
        }

        return json_encode(['listKSongs' => $hostsongs]);
    }
    public function getKaraokeSong($kid) {
        $song = KaraokeSong::where('kid', $kid)->first();

        return json_encode($song);
    }

}
