<?php

namespace App\Http\Controllers\Api;

use App\Relation;
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
            [ 'uid', $uid ],
            [ 'other_uid', $otherId ]
        ])->orWhere([
            [ 'uid', $otherId ],
            [ 'other_uid', $uid ]
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
            [ 'uid', $uid ],
            [ 'other_uid', $otherId ]
        ])->orWhere([
            [ 'uid', $otherId ],
            [ 'other_uid', $uid ]
        ])->first();
        if ($relation == null){
            $relation = new Relation();
            $relation->uid = $uid;
            $relation->other_id = $otherId;
            if ($relation->save()){
                return 1;
            }
        }
        return 0;
    }
}
