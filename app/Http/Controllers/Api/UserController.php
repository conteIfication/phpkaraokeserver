<?php

namespace App\Http\Controllers\Api;

use App\RecordUserKs;
use App\SharedRecording;
use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class UserController extends Controller
{
    //
    public function getRecent() {
        $uid = \Auth::user()->getAttribute('id');

        $records =  RecordUserKs::where('user_id', $uid)->orderBy('updated_at', 'desc')->get();

        foreach ($records as $record) {
            $genreText = '';
            foreach ( $record->karaokesong->genres as $genre ) {
                $genreText .= '&' . $genre->name;
            }
            $record->karaokesong->genre = substr($genreText, 1);

            $artistText = '';
            foreach ( $record->karaokesong->artists as $artist ) {
                $artistText .= '&' . $artist->name;
            }
            $record->karaokesong->artist = substr($artistText, 1);
            $record->song = $record->karaokesong;
        }
        return $records;
    }
    public function removeRecent(Request $request) {
        $ksid = $request->input('ksid');
        $uid = \Auth::user()->getAttribute('id');

        RecordUserKs::where('user_id', $uid)
            ->where('kar_id', $ksid)
            ->delete();
        return 1;
    }
    public function getUser() {
        return \Auth::user();
    }
    public function getSharedRecord($num) {
        $uid = \Auth::user()->getAttribute('id');
        if ($num == 0){
            $datas = SharedRecording::where('user_id', $uid)->orderBy('shared_at', 'desc')->get();
        }else {
            $datas = SharedRecording::where('user_id', $uid)->orderBy('shared_at', 'desc')->take($num)->get();
        }

        foreach ($datas as $data) {
            $data->karaoke;
            $data->user;
        }
        return $datas;
    }
    public function updateAvatar(Request $request) {
        $uid = \Auth::user()->getAttribute('id');
        $path = $request->input('path');

        if (User::where('id', $uid)->update([ 'avatar' => $path ]))
            return 1;

        return 0;
    }
}
