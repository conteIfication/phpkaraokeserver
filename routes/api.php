<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('register', 'Api\Auth\RegisterController@register');
Route::post('login', 'Api\Auth\LoginController@login');
Route::post('refresh', 'Api\Auth\LoginController@refresh');

Route::middleware('auth:api')->group(function (){
    Route::post('logout', 'Api\Auth\LoginController@logout');

    Route::post('songs/all', 'Api\KaraokeSongController@getAll');
    Route::get('songs/new/{num}', 'Api\KaraokeSongController@getNew');
    Route::get('songs/feature/{num}', 'Api\KaraokeSongController@getFeature');
    Route::post('songs/upview', 'Api\KaraokeSongController@upView');
    Route::get('songs/genre', 'Api\KaraokeSongController@getAllGenre');
    Route::get('songs/{id}', 'Api\KaraokeSongController@getOne');
    Route::get('songs/{id}/rank', 'Api\KaraokeSongController@getRank');
    Route::post('songs/playlist', 'Api\KaraokeSongController@getPlaylistsOfSong');
    Route::post('songs/report', 'Api\KaraokeSongController@reportKaraokeSong');

    Route::get('user/recent', 'Api\UserController@getRecent');
    Route::post('user/recent/remove', 'Api\UserController@removeRecent');
    Route::get('user', 'Api\UserController@getUser');
    Route::get('user/shared-records/{num}', 'Api\UserController@getSharedRecord');

    Route::post('record/upload', 'Api\RecordController@uploadAudioRecord');
    Route::post('record/add', 'Api\RecordController@insertShareRecord');
    Route::get('record/popular/{num}', 'Api\RecordController@getPopular');
    Route::post('record/islike', 'Api\RecordController@isLike');
    Route::post('record/like', 'Api\RecordController@like');
    Route::post('record/comment', 'Api\RecordController@comment');
    Route::get('record/{id}/comments', 'Api\RecordController@getComments');
    Route::post('record/report', 'Api\RecordController@reportSharedRecord');
    Route::post('record/upview', 'Api\RecordController@upViewNo');

    Route::post('playlist/add', 'Api\PlaylistController@add');
    Route::post('playlist/save', 'Api\PlaylistController@save');
    Route::post('playlist/delete', 'Api\PlaylistController@delete');
    Route::get('playlist/all', 'Api\PlaylistController@getAll');
    Route::post('playlist/songs', 'Api\PlaylistController@getSongsInPlaylist');

    Route::post('playlist/delsong', 'Api\PlaylistController@deleteSongInPlaylist');
    Route::post('playlist/addsong', 'Api\PlaylistController@addSongToPlaylist');

    Route::get('newfeed/{num}', 'Api\NewFeedController@getNewFeeds');

    Route::get('relation/{other_id}', 'Api\RelationController@getRelation');
    Route::post('relation/request', 'Api\RelationController@request');

    Route::get('announcement/{num}/{sort}', 'Api\AnnouncementController@getAnn');

    Route::post('photo/upload', 'Api\PhotoController@upload');
    Route::post('photo/add', 'Api\PhotoController@add');
    Route::get('photo/all', 'Api\PhotoController@all');
    Route::post('photo/delete', 'Api\PhotoController@delete');

    Route::post('user/update-avatar', 'Api\UserController@updateAvatar');
});



