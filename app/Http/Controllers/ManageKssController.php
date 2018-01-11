<?php

namespace App\Http\Controllers;

use App\Artist;
use App\BelongKsGenre;
use App\Genre;
use App\KaraokeSong;
use App\PerformKsArtist;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Storage;

class ManageKssController extends Controller
{
    //
    public function showAllKss() {
        $kss = KaraokeSong::orderBy('created_at', 'desc')->paginate(10);
        foreach ($kss as $ks){
            $genreText = '';
            foreach ( $ks->genres as $genre ) {
                $genreText .= '&' . $genre->name;
            }
            $ks->genre = substr($genreText, 1);

            $artistText = '';
            foreach ( $ks->artists as $artist ) {
                $artistText .= '&' . $artist->name;
            }
            $ks->artist = substr($artistText, 1);
        }

        return view('manage-kss', ['kss' => $kss]);
    }
    public function getKsDetail($id) {
        $ks = KaraokeSong::find($id);
        $genreText = '';
        foreach ( $ks->genres as $genre ) {
            $genreText .= '&' . $genre->name;
        }
        $ks->genre = substr($genreText, 1);

        $artistText = '';
        foreach ( $ks->artists as $artist ) {
            $artistText .= '&' . $artist->name;
        }
        $ks->artist = substr($artistText, 1);

        return $ks;
    }
    public function deleteKs($id) {
        $ks = KaraokeSong::find($id);
        $folderName = substr( $ks->beat, 0, strlen($ks->beat) - 4 );

        Storage::disk('public')->delete( 'songs/' . $folderName . '/' . $ks->beat );
        Storage::disk('public')->delete( 'songs/' . $folderName . '/' . $ks->lyric );
        Storage::disk('public')->delete( 'songs/' . $folderName . '/' . $ks->image );

        if(KaraokeSong::where('id', $id)->delete())
            return 1;
        return 0;
    }

    public function showUploadForm() {
        $genres = Genre::all();
        $artists = Artist::all();
        return view('ks-upload', ['genres' => $genres, 'artists' => $artists]);
    }

    public function uploadKs(Request $request) {
        $cb_artists = $request->input('cb_artists');
        $cb_genres = $request->input('cb_genres');
        $name = $request->input('ks_name');
        if ($name == ''
            || $cb_artists == null || count($cb_artists) == 0
            || $cb_genres == null || count($cb_genres) == 0){
            return 0;
        }
        /*if ($request->hasFile('beat')){
            echo 'beat';
        }
        if ($request->hasFile('lyric')){
            echo 'lyric';
        }
        if ($request->hasFile('image')){
            echo 'image';
        }
        return 1;*/

        if($request->hasFile('beat')
            && $request->hasFile('lyric')
            && $request->hasFile('image')){

            $genres = Genre::whereIn('id', $cb_genres)->get();
            $artists = Artist::whereIn('id', $cb_genres)->get();

            $folderName = $name . ' - ';
            foreach ($artists as $artist){
                $folderName .= $artist->name . '&';
            }
            $folderName = substr($folderName, 0, strlen($folderName) - 1);

            //save file
            $lyric = $request->file('lyric')->storeAs('public/songs/' . $folderName, $folderName . '.txt' );
            $image = $request->file('image')->storeAs('public/songs/' . $folderName, $folderName . '.jpg' );
            $beat = $request->file('beat')->storeAs('public/songs/' . $folderName, $folderName . '.mp3' );

            //add to database
            $ks = new KaraokeSong();
            $ks->name = $name;
            $ks->beat = $folderName . '.mp3';
            $ks->lyric = $folderName . '.txt';
            $ks->image = $folderName . '.jpg';
            $ks->year = $request->ks_year;

            if ($ks->save()){
                foreach ($genres as $genre){
                    $belongKsGenre = new BelongKsGenre();
                    $belongKsGenre->genre_id = $genre->id;
                    $belongKsGenre->kar_id = $ks->id;
                    $belongKsGenre->save();
                }

                foreach ($artists as $artist){
                    $performKsArtist = new PerformKsArtist();
                    $performKsArtist->kar_id = $ks->id;
                    $performKsArtist->art_id = $artist->id;
                    $performKsArtist->save();
                }

                return redirect()->route('admin.manage.kss');
            }
        }
        return 0;
    }
    public function editKs($id) {
        $genres = Genre::all();
        $artists = Artist::all();
        $ks = KaraokeSong::find($id);
        return view('ks-edit', ['ks' => $ks, 'genres' => $genres, 'artists' => $artists ]);
    }
    public function saveEditKs($id, Request $request) {
        $cb_artists = $request->input('cb_artists');
        $cb_genres = $request->input('cb_genres');
        $name = $request->input('ks_name');
        $year = $request->input('ks_year');
        if ($name == ''
            || $cb_artists == null || count($cb_artists) == 0
            || $cb_genres == null || count($cb_genres) == 0){
            return 0;
        }
        //delete genres
        BelongKsGenre::where('kar_id', $id)->delete();
        //delete artists
        PerformKsArtist::where('kar_id', $id)->delete();

        //add new genres and new artist
        foreach ($cb_genres as $genre_id){
            $belongKsGenre = new BelongKsGenre();
            $belongKsGenre->genre_id = $genre_id;
            $belongKsGenre->kar_id = $id;
            $belongKsGenre->save();
        }

        foreach ($cb_artists as $artist_id){
            $performKsArtist = new PerformKsArtist();
            $performKsArtist->kar_id = $id;
            $performKsArtist->art_id = $artist_id;
            $performKsArtist->save();
        }
        //update year and name
        $karaokeSong = KaraokeSong::where('id', $id)->first();
        $karaokeSong->name = $name;
        $karaokeSong->year = $year;
        if($karaokeSong->save()){
            return redirect()->back();
        }
        return 0;
    }
    public function addGenre(Request $request) {
        $newGenre = new Genre();
        $newGenre->name = $request->input('genre_name');
        if($newGenre->save()){
            return redirect()->back();
        }
        return 0;
    }
    public function addArtist(Request $request) {
        $newArtist = new Artist();
        $newArtist->name = $request->input('artist_name');

        if ($newArtist->save())
            return redirect()->back();
        return 0;
    }
}
