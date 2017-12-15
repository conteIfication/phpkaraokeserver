<?php

namespace App\Http\Controllers\Api;

use App\BelongKsGenre;
use App\Genre;
use App\KaraokeSong;
use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class KaraokeSongController extends Controller
{

     public function getAll() {

         $datas = KaraokeSong::all();
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
}
