<?php

namespace App\Http\Controllers\Api;

use App\RecordUserKs;
use App\SharedRecording;
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
    public function getSharedRecord() {
        $uid = \Auth::user()->getAttribute('id');
        $datas = SharedRecording::where('user_id', $uid)->get();
        foreach ($datas as $data) {
            $data->song = $data->karaoke;
        }
        return $datas;
    }
}
