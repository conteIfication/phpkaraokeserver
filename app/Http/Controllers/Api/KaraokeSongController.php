<?php

namespace App\Http\Controllers\Api;

use App\Genre;
use App\HasPlaylistKs;
use App\KaraokeSong;
use App\RecordUserKs;

use App\Http\Controllers\Controller;
use App\ReportUserKs;
use App\SharedRecording;
use App\User;
use Carbon\Carbon;
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
            $datas = KaraokeSong::orderBy('recent_view_no', 'desc')->get();
        }else if ($num > 0){
            $datas = KaraokeSong::orderBy('recent_view_no', 'desc')->take($num)->get();
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

        if ($record->first() == null) {
            $newRecord = new RecordUserKs();
            $newRecord->user_id = $uid;
            $newRecord->kar_id = $ksid;
            $newRecord->count = 1;
            $newRecord->save();
        }else{
            $recordInner = $record->first();
            $date_diff = date_diff( $recordInner->created_at, $recordInner->updated_at );

            if ($date_diff->d > 7){
                $record->update([ 'created_at' => Carbon::now(),
                    'count' => 1
                ]);
            }else {
                $count = $recordInner->count;
                $record->update(['count' => $count + 1]);
            }
        }

        //Update karaokeSong
        $karaokeSong = KaraokeSong::where('id', $ksid)->first();
        $viewNo = $karaokeSong->view_no;
        $date_diff = date_diff( $karaokeSong->created_at, $karaokeSong->updated_at );
        if ($date_diff->d > 7){
            KaraokeSong::where('id', $ksid)->update([
                'view_no' => $viewNo + 1,
                'recent_view_no' => 1,
                'created_at' => Carbon::now()
            ]);
        }else {
            $recent_view_no = $karaokeSong->recent_view_no;
            KaraokeSong::where('id', $ksid)->update([
                'view_no' => $viewNo + 1,
                'recent_view_no' => $recent_view_no + 1
            ]);
        }
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
    public function getRecommend($num) {
        $N = 3;
        $uid = \Auth::user()->getAttribute('id');
        $recent_kss = RecordUserKs::where('user_id', $uid)->orderBy('updated_at', 'desc')->get();
        if ( $recent_kss->count() < $N ){
            //content-based filter
            //get feature songs
            $feature_songs = KaraokeSong::orderBy('recent_view_no', 'desc')->take($num)->get();
            $arr_feature_songs = array();
            foreach ($feature_songs as $feature_song){
                array_push($arr_feature_songs, $feature_song->id);
            }
            $diff_feature_songs = KaraokeSong::whereNotIn('id', $arr_feature_songs)->get();
            //compare featureSongs with diffSongs to ranking
            $songs = array();
            foreach ($feature_songs as $feature_song ){
                $maxScore = -10;
                $song = null;
                foreach ($diff_feature_songs as $diff_feature_song) {
                    $score = 0;
                    //genre diff
                    $genre_score = 0;
                    foreach ( $diff_feature_song->genres as $d_f_genre ) {
                        foreach ($feature_song->genres as $f_genre){
                            if ($d_f_genre->name == $f_genre->name){
                                $genre_score++;
                            }
                        }
                    }
                    $genre_score = $genre_score * 2.0 / (count($diff_feature_song->genres)
                            + count($feature_song->genres));
                    $artist_score = 0;
                    foreach ( $diff_feature_song->artists as $d_f_artist ) {
                        foreach ($feature_song->artists as $f_artist){
                            if ($d_f_artist->name == $f_artist->name){
                                $artist_score++;
                            }
                        }
                    }
                    $artist_score = $artist_score * 2.0 / (count($diff_feature_song->genres)
                            + count($feature_song->genres));

                    //year
                    $year_score = abs($feature_song->year - $diff_feature_song->year);
                    if ($year_score > 10) {
                        $year_score = 0;
                    }else {
                        $year_score = 1 - $year_score/10.0;
                    }

                    //c_score  //counter score
                    $c_score = 0;
                    $item = RecordUserKs::where('kar_id', $diff_feature_song->id)->first();
                    if ($item != null){
                        $c_score = $item->count/10;
                    }

                    $score = $genre_score + $artist_score + $c_score;

                    if ($score > $maxScore){
                        $noExist = true;
                        foreach ( $songs as $s ) {
                            if ($s->id == $diff_feature_song->id){
                                $noExist = false;
                                break;
                            }
                        }
                        if ($noExist) {
                            $maxScore = $score;
                            $song = $diff_feature_song;
                        }
                    }
                }
                if ($song != null){
                    array_push($songs, $song);
                }
            }
            return $songs;

        }
        else {
            //hyrid recommend
            $recent_ks_ids = array();
            for ($i = 0; $i < $N; $i++){
                array_push($recent_ks_ids, $recent_kss[$i]->kar_id);
            }
            //tim tat ca nguoi dung cung hat chung
            $user_ids = array();
            $users = User::where('id', '<>', $uid)->get();
            foreach ($users as $user) {
                $count = RecordUserKs::where('user_id', $user->id)
                    ->whereIn('kar_id', $recent_ks_ids)->count();
                if ($count == count($recent_ks_ids)){
                    array_push($user_ids, $user->id);
                }
            }
            $kar_ids = array();
            $kar_ids_repeat = array();
            foreach ( $user_ids as $user_id ){
                $karSongs =  RecordUserKs::where('user_id', $user_id)
                    ->whereNotIn('kar_id', $recent_ks_ids)->get();
                foreach ( $karSongs as $karSong ){
                    $exist = false;
                    for($i = 0; $i < count($kar_ids); $i++){
                        if ($kar_ids[$i] == $karSong->kar_id){
                            $exist = true;
                            break;
                        }
                    }
                    if ($exist) {
                        $kar_ids_repeat[$i] = $kar_ids_repeat[$i] + 1;
                    }else {
                        array_push( $kar_ids, $karSong->kar_id );
                        array_push( $kar_ids_repeat, 1 );
                    }
                }
            }
            $result = array();
            if (count($kar_ids_repeat) < $num){
                $num = count($kar_ids_repeat);
            }

            for ($j = 0; $j < $num; $j++){
                $max = -1;
                $idxMax = -1;

                for ($k = 0; $k < count($kar_ids_repeat); $k++){
                    if ($max < $kar_ids_repeat[$k]){
                        $max = $kar_ids_repeat[$k];
                        $idxMax = $k;
                    }
                }
                if ($idxMax >= 0){
                    array_push($result, KaraokeSong::find($kar_ids[$idxMax]));
                    $kar_ids_repeat[$idxMax] = -10;
                }
            }
            if (count($result) < 3)
                return $result;
            //3 bai content-based
            //get feature songs
            $feature_songs = KaraokeSong::whereIn('id',
                [$result[0]->id, $result[1]->id, $result[2]->id])->get();
            $arr_feature_songs = array();
            foreach ($feature_songs as $feature_song){
                array_push($arr_feature_songs, $feature_song->id);
            }
            $diff_feature_songs = KaraokeSong::whereNotIn('id', $arr_feature_songs)->get();
            //compare featureSongs with diffSongs to ranking
            $songs = array();
            foreach ($feature_songs as $feature_song ){
                $maxScore = -10;
                $song = null;
                foreach ($diff_feature_songs as $diff_feature_song) {
                    $score = 0;
                    //genre diff
                    $genre_score = 0;
                    foreach ( $diff_feature_song->genres as $d_f_genre ) {
                        foreach ($feature_song->genres as $f_genre){
                            if ($d_f_genre->name == $f_genre->name){
                                $genre_score++;
                            }
                        }
                    }
                    $genre_score = $genre_score * 2.0 / (count($diff_feature_song->genres)
                            + count($feature_song->genres));
                    $artist_score = 0;
                    foreach ( $diff_feature_song->artists as $d_f_artist ) {
                        foreach ($feature_song->artists as $f_artist){
                            if ($d_f_artist->name == $f_artist->name){
                                $artist_score++;
                            }
                        }
                    }
                    $artist_score = $artist_score * 2.0 / (count($diff_feature_song->genres)
                            + count($feature_song->genres));

                    //year
                    $year_score = abs($feature_song->year - $diff_feature_song->year);
                    if ($year_score > 10) {
                        $year_score = 0;
                    }else {
                        $year_score = 1 - $year_score/10.0;
                    }

                    //c_score  //counter score
                    $c_score = 0;
                    $item = RecordUserKs::where('kar_id', $diff_feature_song->id)->first();
                    if ($item != null){
                        $c_score = $item->count/10;
                    }

                    $score = $genre_score + $artist_score + $c_score;

                    if ($score > $maxScore){
                        $noExist = true;
                        foreach ( $songs as $s ) {
                            if ($s->id == $diff_feature_song->id){
                                $noExist = false;
                                break;
                            }
                        }
                        if ($noExist) {
                            $maxScore = $score;
                            $song = $diff_feature_song;
                        }
                    }
                }
                if ($song != null){
                    array_push($songs, $song);
                }
            }
            $songs;
            foreach ($songs as $song){
                $exist = false;
                foreach ($result as $item){
                    if ($item->id == $song->id){
                        $exist = true;
                        break;
                    }
                }
                if (!$exist){
                    array_push($result, $song);
                }
            }

            return $result;
        }
    }

    public function sendNotLike(Request $request) {
        $uid = \Auth::user()->getAttribute('id');
        $ksid = $request->input('ks_id');

        $record = RecordUserKs::where('user_id', $uid)->where('kar_id', $ksid);
        if ($record->first() != null) {
            $c = $record->first()->count;
            $record->update([ 'count' => $c - 5 ]);
        }
        return 1;
    }
    public function searchKs(Request $request) {
        $name = $request->input('name');

        $datas = KaraokeSong::where('name', 'like', '%'.$name.'%')->get();
        foreach ($datas as $data){
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
}
