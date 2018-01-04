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

        if($request->hasFile('beat')
            && $request->hasFile('lyric')
            && $request->hasFile('image')){

            if ($request->ks_name == '' || !is_numeric($request->ks_year)){
                return 0;
            }

            $name = $request->ks_name;
            $genre = Genre::find($request->ks_genre);
            $artist = Artist::find($request->ks_artist);

            $folderName = $name . ' - ' . $artist->name;

            //save file
            $beat = $request->file('beat')->storeAs('public/songs/' . $folderName, $folderName . '.mp3' );
            $lyric = $request->file('lyric')->storeAs('public/songs/' . $folderName, $folderName . '.txt' );
            $image = $request->file('lyric')->storeAs('public/songs/' . $folderName, $folderName . '.jpg' );

            //add to database
            $ks = new KaraokeSong();
            $ks->name = $name;
            $ks->beat = $folderName . '.mp3';
            $ks->lyric = $folderName . '.txt';
            $ks->image = $folderName . '.jpg';
            $ks->year = $request->ks_year;

            if ($ks->save()){
                $belongKsGenre = new BelongKsGenre();
                $belongKsGenre->genre_id = $genre->id;
                $belongKsGenre->kar_id = $ks->id;
                $belongKsGenre->save();

                $performKsArtist = new PerformKsArtist();
                $performKsArtist->kar_id = $ks->id;
                $performKsArtist->art_id = $artist->id;
                $performKsArtist->save();

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

    }
}
