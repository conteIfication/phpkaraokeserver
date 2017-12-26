<?php

namespace App\Http\Controllers\Api;

use App\HasPlaylistKs;
use App\Playlist;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class PlaylistController extends Controller
{
    //
    public function add(Request $request) {
        $name = $request->input('name');

        $pl = new Playlist();
        $pl->name = $name;
        $pl->user_id = \Auth::user()->getAttribute('id');
        if ($pl->save()){
            return $pl;
        }
        return null;
    }

    public function save(Request $request) {
        $name = $request->input('name');
        $id = $request->input('id');

        $pl = Playlist::where('id', $id)->first();
        if ($pl != null) {
            $pl->name = $name;
            if ($pl->save()) {
                return 1;
            }
        }
        return 0;
    }

    public function delete(Request $request) {
        $plid = $request->input('id');
        $pl = Playlist::where('id', $plid)->where('user_id', \Auth::user()->getAttribute('id'))
            ->first();
        if ($pl != null) {
            if ($pl->delete()) {
                return 1;
            }
        }
        return 0;
    }

    public function getAll() {
        $datas = Playlist::where('user_id', \Auth::user()->getAttribute('id'))->get();
        foreach ($datas as $data) {
            $hasPlaylistKs = HasPlaylistKs::where('pl_id', $data->id)->get();
            $data->num_songs = $hasPlaylistKs->count();
        }
        return $datas;
    }

    public function getSongsInPlaylist(Request $request){
        $playlistId = $request->input('pl_id');
        $datas = HasPlaylistKs::where('pl_id', $playlistId)->get();
        foreach ($datas as $data) {
            $data->karaokesong;

            $genreText = '';
            foreach ( $data->karaokesong->genres as $genre ) {
                $genreText .= '&' . $genre->name;
            }
            $data->karaokesong->genre = substr($genreText, 1);

            $artistText = '';
            foreach ( $data->karaokesong->artists as $artist ) {
                $artistText .= '&' . $artist->name;
            }
            $data->karaokesong->artist = substr($artistText, 1);

        }
        return $datas;
    }

    public function deleteSongInPlaylist(Request $request){
        $plid = $request->input('pl_id');
        $ksid = $request->input('kar_id');

        $hasPlaylistKs = HasPlaylistKs::where('pl_id', $plid)->where('kar_id', $ksid)->first();
        if ($hasPlaylistKs != null) {
            HasPlaylistKs::where('pl_id', $plid)->where('kar_id', $ksid)->delete();
            return 1;
        }
        return 0;
    }
    public function addSongToPlaylist(Request $request) {
        $plid = $request->input('pl_id');
        $ksid = $request->input('kar_id');

        $hasPlaylistKs = new HasPlaylistKs();
        $hasPlaylistKs->pl_id = $plid;
        $hasPlaylistKs->kar_id = $ksid;
        if ($hasPlaylistKs->save()) {
            return 1;
        }
        return 0;
    }
}
