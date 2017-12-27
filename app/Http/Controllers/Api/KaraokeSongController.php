<?php

namespace App\Http\Controllers\Api;

use App\Genre;
use App\HasPlaylistKs;
use App\KaraokeSong;
use App\RecordUserKs;

use App\Http\Controllers\Controller;
use App\ReportUserKs;
use App\SharedRecording;
use Illuminate\Http\Request;

class KaraokeSongController extends Controller
{
    public function getAll(Request $request) {
        $sort = $request->input('sort');
        $genreRequest = $request->input('genre'); //pop-hiphop-...
        $genreRequestArray = explode("_", $genreRequest);

        switch ($sort) {
            case 'desc-time':
                $datas = KaraokeSong::orderBy('created_at', 'desc')->get();
                break;
            case 'asc-time':
                $datas = KaraokeSong::orderBy('created_at')->get();
                break;
            case 'desc-name':
                $datas = KaraokeSong::orderBy('name', 'desc')->get();
                break;
            case 'asc-name':
                $datas = KaraokeSong::orderBy('name')->get();
                break;
            case 'none':
            default:
                $datas = KaraokeSong::all();
                break;
        }
        $res = array();
        foreach ($datas as $data) {
            if ( $genreRequest !== '' && $genreRequest !== "all" ) {
                $genreText = '';
                $isAdd = false;
                foreach ( $data->genres as $genre ) {
                    $genreText .= '&' . $genre->name;

                    foreach ($genreRequestArray as $item) {
                        if ($item === $genre->name){
                            $isAdd = true;
                        }
                    }
                }
                $data->genre = substr($genreText, 1);

                $artistText = '';
                foreach ( $data->artists as $artist ) {
                    $artistText .= '&' . $artist->name;
                }
                $data->artist = substr($artistText, 1);

                if ($isAdd) {
                    array_push($res, $data);
                }
            } else {
                $genreText = '';
                foreach ( $data->genres as $genre ) {
                    $genreText .= '&' . $genre->name;
                }
                $data->genre = substr($genreText, 1);

                $artistText = '';
                foreach ( $data->artists as $artist ) {
                    $artistText .= '&' . $artist->name;
                }
                $data->artist = substr($artistText, 1);
                array_push($res, $data);
            }
        }
        return $res;
    }

    public function getNew($num) {
         if ($num == 0) {
             $datas = KaraokeSong::orderBy('created_at', 'desc')->get();
         }else if ($num > 0){
             $datas = KaraokeSong::orderBy('created_at', 'desc')->take($num)->get();
         }


        foreach ($datas as $data) {
            $genreText = '';
            foreach ( $data->genres as $genre ) {
                $genreText .= '&' . $genre->name;
            }
            $data->genre = substr($genreText, 1);

            $artistText = '';
            foreach ( $data->artists as $artist ) {
                $artistText .= '&' . $artist->name;
            }
            $data->artist = substr($artistText, 1);
        }
        return $datas;
    }

    public function getFeature($num) {
        if ($num == 0) {
            $datas = KaraokeSong::orderBy('view_no', 'desc')->get();
        }else if ($num > 0){
            $datas = KaraokeSong::orderBy('view_no', 'desc')->take($num)->get();
        }

        foreach ($datas as $data) {
            $genreText = '';
            foreach ( $data->genres as $genre ) {
                $genreText .= '&' . $genre->name;
            }
            $data->genre = substr($genreText, 1);

            $artistText = '';
            foreach ( $data->artists as $artist ) {
                $artistText .= '&' . $artist->name;
            }
            $data->artist = substr($artistText, 1);
        }
        return $datas;
    }

    public function upView(Request $request) {
        $uid = \Auth::user()->getAttribute('id');
        $ksid = $request->input('ksid');
        //Update record_user_ks
        $record = RecordUserKs::where('user_id', $uid)
            ->where('kar_id', $ksid);

        if ($record->count() == 0) {
            $newRecord = new RecordUserKs();
            $newRecord->user_id = $uid;
            $newRecord->kar_id = $ksid;
            $newRecord->count = 1;
            $newRecord->save();
        }else{
            $count = $record->first()->count;
            $record->update(['count' => $count + 1]);
        }
        //Update karaokesong view_no
        $viewNo = KaraokeSong::where('id', $ksid)
            ->first()->view_no;
        KaraokeSong::where('id', $ksid)
            ->update(['view_no' => $viewNo + 1]);

        return 1;
    }
    public function getAllGenre() {
        return Genre::all();
    }
    public function getOne($id) {
        return KaraokeSong::find($id);
    }
    public function getRank($id) {
        $datas = SharedRecording::where('kar_id', $id)
            ->orderBy('view_no', 'desc')
            ->take(10)
            ->get();
        foreach ($datas as $data) {
            $data->user;
            $data->karaoke;
        }
        return $datas;
    }

    public function getPlaylistsOfSong(Request $request) {
        $ksid = $request->input('kar_id');
        $uid = \Auth::user()->getAttribute('id');

        $result = array();
        $datas = HasPlaylistKs::where('kar_id', $ksid)->get();
        foreach ($datas as $data) {
            if ($data->playlist->user_id == $uid){
                array_push( $result, $data );
            }
        }
        return $result;
    }
    public function reportKaraokeSong(Request $request) {
        /* 0: fail
           1: success
           2: exist
           3: update
         * */
        $ksid = $request->input('kar_id');
        $uid = \Auth::user()->getAttribute('id');
        $subject = $request->input('subject');

        $report = ReportUserKs::where('kar_id', $ksid)->where('user_id', $uid)->first();
        if ($report != null){
            if ($report->subject == $subject){
                return 2;
            }

            if (ReportUserKs::where('kar_id', $ksid)
                ->where('user_id', $uid)
                ->update([ 'subject' => $subject ])){
                return 3;
            }else {
                return 0;
            }
        }
        $newReport = new ReportUserKs();
        $newReport->kar_id = $ksid;
        $newReport->user_id = $uid;
        $newReport->subject = $subject;
        if ($newReport->save()){
            return 1;
        }
        return 0;
    }
}
