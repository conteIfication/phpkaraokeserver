<?php

namespace App\Http\Controllers\Api;

use App\ToAnnUser;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class AnnouncementController extends Controller
{
    //
    public function getAnn($num, $sort) {
        $uid = \Auth::user()->getAttribute('id');
        if ($num == 0) {
            //get all
            if ($sort == 'desc') {
                $datas = ToAnnUser::where('user_id', $uid)->orderBy('created_at', 'desc')->get();
            }else {
                $datas = ToAnnUser::where('user_id', $uid)->orderBy('created_at')->get();
            }
        }else {
            if ($sort == 'desc'){
                $datas = ToAnnUser::where('user_id', $uid)->take($num)->orderBy('created_at', 'desc')->get();
            }else {
                $datas = ToAnnUser::where('user_id', $uid)->take($num)->orderBy('created_at')->get();
            }
        }
        foreach ($datas as $data){
            $data->announcement;
            $data->user;
        }
        return $datas;
    }
}
