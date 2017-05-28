<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::POST('login', 'PageController@login')->name('login');

Route::post('logout', 'Auth\LoginController@logout')->name('logout');
Route::get('logoutSuccess', 'PageController@logoutSuccess')->name('logoutSuccess');

Route::post('register', 'PageController@register')->name('register');


Route::get('/test', 'PageController@show')->name('test');
