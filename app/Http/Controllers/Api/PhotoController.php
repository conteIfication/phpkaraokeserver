<?php

namespace App\Http\Controllers\Api;

use App\Photo;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class PhotoController extends Controller
{
    //
    public function upload(Request $request) {
        $path = $request->file('myfile')->store('public/photos');
        return $path;
    }
    public function add(Request $request){
        $uid = \Auth::user()->getAttribute('id');
        $fileName = $request->input('file_name');

        $photo = new Photo();
        $photo->path = $fileName;
        $photo->user_id = $uid;
        if ($photo->save()){
            return $photo;
        }
        return null;
    }
    public function all() {
        $uid = \Auth::user()->getAttribute('id');
        return Photo::where('user_id', $uid)->get();
    }
    public function delete(Request $request){
        $photoId = $request->input('photo_id');
        if (Photo::where('id', $photoId)->delete()){
            return 1;
        }
        return 0;
    }
}
