<?php

namespace App\Http\Controllers\Api;

use App\SharedRecord;
use App\SharedRecording;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class RecordController extends Controller
{
    //
    public function uploadAudioRecord(Request $request)
    {

        $path = $request->file('myfile')->store('public/audio-records');
        return $path;
    }
    public function insertShareRecord(Request $request) {
        $ksid = $request->input('ksid');
        $type = $request->input('type');
        $path = $request->input('path');
        $score = $request->input('score');
        $content = $request->input('content');
        $s = new SharedRecording();
        $s->content = $content;
        $s->score = $score;
        $s->path = $path;
        $s->type = $type;
        $s->kar_id = $ksid;
        $s->user_id = \Auth::user()->getAttribute('id');

        return $s->save() ? 1 : 0;
    }

    public function getPopular( $num ) {
        if ($num == 0) {
            $datas = SharedRecording::orderBy('view_no', 'desc')->get();
            foreach ($datas as $data) {
                $data->karaoke;
                $data->user;
            }
        }else {
            $datas = SharedRecording::orderBy('view_no', 'desc')->take($num)->get();
            foreach ($datas as $data) {
                $data->karaoke;
                $data->user;
            }
        }
        return $datas;

    }
}
