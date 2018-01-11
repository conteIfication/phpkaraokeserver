<?php

namespace App\Http\Controllers\Api;

use App\CommentTb;
use App\LikeUserSr;
use App\ReportUserSr;
use App\SharedRecord;
use App\SharedRecording;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class RecordController extends Controller
{
    //
    public function uploadAudioRecord(Request $request)
    {
        $path = $request->file('myfile')->store('public/records');
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

    public function deleteShareRecord(Request $request) {
        $srid = $request->input('srid');
        if (SharedRecording::where('id', $srid)->delete()){
            return 1;
        }
        return 0;
    }

    public function getPopular( $num ) {
        if ($num == 0) {
            $datas = SharedRecording::orderBy('view_no', 'desc')->get();
            foreach ($datas as $data) {
                $data->karaoke;
                $data->user;
                $data->num_likes = LikeUserSr::where('sr_id', $data->id )->get()->count();
                $data->num_comments = CommentTb::where('sr_id', $data->id )->get()->count();
            }
        }else {
            $datas = SharedRecording::orderBy('view_no', 'desc')->take($num)->get();
            foreach ($datas as $data) {
                $data->karaoke;
                $data->user;
                $data->num_likes = LikeUserSr::where('sr_id', $data->id )->get()->count();
                $data->num_comments = CommentTb::where('sr_id', $data->id )->get()->count();
            }
        }
        return $datas;
    }

    public function isLike(Request $request) {
        $srid = $request->input('sr_id');
        $uid = \Auth::user()->getAttribute('id');

        $like = LikeUserSr::where('user_id', $uid)->where('sr_id', $srid)->first();
        if ( $like != null ){
            return 1;
        }
        return 0;
    }
    public function like(Request $request) {
        $srid = $request->input('sr_id');
        $uid = \Auth::user()->getAttribute('id');

        $like = LikeUserSr::where('user_id', $uid)->where('sr_id', $srid)->first();
        if ( $like != null ){
            //remove like
            LikeUserSr::where('user_id', $uid)->where('sr_id', $srid)->delete();
            return 0;
        }
        //add like
        $newLike = new LikeUserSr();
        $newLike->sr_id = $srid;
        $newLike->user_id = $uid;
        if ($newLike->save()){
            return 1;
        }
        return 0;
    }

    public function comment(Request $request) {
        $srid = $request->input('sr_id');
        $uid = \Auth::user()->getAttribute('id');
        $content = $request->input('content');

        $comment = new CommentTb();
        $comment->user_id = $uid;
        $comment->sr_id = $srid;
        $comment->content = $content;

        if ($comment->save()) {
            $newComment = CommentTb::find($comment->id);
            $newComment->user;

            return $newComment;
        }
        return null;
    }
    public function getComments($srid) {
        $datas = CommentTb::where('sr_id', $srid)->get();
        foreach ($datas as $data) {
            $data->user;
        }
        return $datas;
    }
    public function reportSharedRecord(Request $request){
        /* 0: fail
           1: success
           2: exist
           3: update
         * */
        $srid = $request->input('sr_id');
        $uid = \Auth::user()->getAttribute('id');
        $subject = $request->input('subject');

        $report = ReportUserSr::where('sr_id', $srid)->where('user_id', $uid)->first();
        if ($report != null){
            if ($report->subject == $subject){
                return 2;
            }

            if (ReportUserSr::where('sr_id', $srid)
                ->where('user_id', $uid)
                ->update([ 'subject' => $subject ])){
                return 3;
            }else {
                return 0;
            }
        }
        $newReport = new ReportUserSr();
        $newReport->sr_id = $srid;
        $newReport->user_id = $uid;
        $newReport->subject = $subject;
        if ($newReport->save()){
            return 1;
        }
        return 0;

    }
    public function upViewNo(Request $request) {
        $srId = $request->input('sr_id');

        $sr = SharedRecording::find($srId);
        $sr->view_no = $sr->view_no + 1;
        if ($sr->save()){
            return 1;
        }
        return 0;
    }
    public function getSr($id) {
        $data = SharedRecording::find($id);
        $data->user;
        $data->karaoke;
        $data->num_likes = LikeUserSr::where('sr_id', $id )->get()->count();
        $data->num_comments = CommentTb::where('sr_id', $id )->get()->count();
        return $data;
    }
}
