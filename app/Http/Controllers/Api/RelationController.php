<?php

namespace App\Http\Controllers\Api;

use App\Announcement;
use App\Relation;
use App\ToAnnUser;
use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class RelationController extends Controller
{
    //
    /*
     * 0: same
     * 1: friend
     * 2: request
     * 3: nofriend
     * */


    public function getRelation($otherId) {
        $uid = \Auth::user()->getAttribute('id');

        if ($uid == $otherId)
            return 0;

        $relation = Relation::where([
            [ 'user_id', $uid ],
            [ 'other_id', $otherId ]
        ])->orWhere([
            [ 'user_id', $otherId ],
            [ 'other_id', $uid ]
        ])->first();
        if ($relation != null){
            if ($relation->status == 'request')
                return 2;
            if ($relation->status == 'friend')
                return 1;

        }
        return 3;
    }

    public function request(Request $request){
        $uid = \Auth::user()->getAttribute('id');
        $otherId = $request->input('other_id');

        //check
        $relation = Relation::where([
            [ 'user_id', $uid ],
            [ 'other_id', $otherId ]
        ])->orWhere([
            [ 'user_id', $otherId ],
            [ 'other_id', $uid ]
        ])->first();
        if ($relation == null){
            $relation = new Relation();
            $relation->user_id = $uid;
            $relation->other_id = $otherId;
            if ($relation->save()){

                //announcement
                $ann = new Announcement();
                $ann->content =  User::find($uid)->name . ' has sent a friend request for '
                    . User::find($otherId)->name;
                $ann->admin_id = 3;
                if ($ann->save()){
                    $toAnnUser = new ToAnnUser();
                    $toAnnUser->ann_id = $ann->id;
                    $toAnnUser->user_id = $otherId;
                    $toAnnUser-save();
                }

                return 1;
            }
        }
        return 0;
    }

    public function all() {
        $uid = \Auth::user()->getAttribute('id');

        $datas =  Relation::where('user_id', $uid)->orWhere('other_id', $uid)->get();
        foreach ($datas as $data){
            if ($data->user->id == $uid){
                $data->other = $data->other_user;
            }else {
                $data->other = $data->user;
            }
        }
        return $datas;
    }
}
