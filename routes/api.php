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

    Route::get('user/recent', 'Api\UserController@getRecent');
    Route::post('user/recent/remove', 'Api\UserController@removeRecent');
    Route::get('user', 'Api\UserController@getUser');
    Route::get('user/shared-records', 'Api\UserController@getSharedRecord');

    Route::post('record/upload', 'Api\RecordController@uploadAudioRecord');
    Route::post('record/add', 'Api\RecordController@insertShareRecord');
    Route::get('record/popular/{num}', 'Api\RecordController@getPopular');
});



