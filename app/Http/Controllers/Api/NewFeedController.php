<?php

namespace App\Http\Controllers\Api;

use App\CommentTb;
use App\LikeUserSr;
use App\Relation;
use App\SharedRecording;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class NewFeedController extends Controller
{
    //

    public function getNewFeeds($num) {
        $uid = \Auth::user()->getAttribute('id');

        $relations = Relation::where('uid', $uid)
            ->orWhere('other_uid', $uid)
            ->where('status', 'friend')
            ->orWhere('status', 'request')->get();

        $uid_arr = array($uid);
        foreach ($relations as $relation){
            if ($relation->uid != $uid){
                array_push( $uid_arr, $relation->uid  );
            }else {
                array_push( $uid_arr, $relation->other_uid  );
            }
        }
        $datas = SharedRecording::whereIn('user_id', $uid_arr)->orderBy('shared_at', 'desc')->take($num)->get();

        foreach ($datas as $data) {
            $data->karaoke;
            $data->user;
            $data->num_likes = LikeUserSr::where('sr_id', $data->id )->get()->count();
            $data->num_comments = CommentTb::where('sr_id', $data->id )->get()->count();

        }
        return $datas;
    }
}
