<?php

namespace App\Http\Controllers;

use App\CommentTb;
use App\LikeUserSr;
use App\SharedRecording;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class ManageSrsController extends Controller
{
    //
    public function showAllSrs() {
        $srs = SharedRecording::orderBy('shared_at', 'desc')->paginate(10);
        foreach ($srs as $sr){
            $sr->user;
            $sr->karaoke;
        }
        return view('manage-srs', ['srs' => $srs]);
    }
    public function getSrDetail($srid) {
        $sr = SharedRecording::find($srid);
        if ($sr == null)
            return '';
        $sr->user;
        $sr->karaoke;
        $sr->num_likes = LikeUserSr::where('sr_id', $srid )->get()->count();
        $sr->num_comments = CommentTb::where('sr_id', $srid )->get()->count();
        return $sr;
    }
    public function deleteSr($id) {
        $sr = SharedRecording::find($id);

        Storage::disk('public')->delete( 'records/' . $sr->path );

        if (SharedRecording::where('id', $id)->delete())
            return 1;
        return 0;
    }
}
