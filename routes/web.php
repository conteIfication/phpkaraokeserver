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


Auth::routes();
Route::get('/', 'HomeController@index');
Route::get('/home', 'HomeController@index')->name('home');

Route::prefix('/admin')->group(function (){
    Route::get('/login', 'Auth\AdminLoginController@showLoginForm')->name('admin.login');
    Route::post('/login', 'Auth\AdminLoginController@login')->name('admin.login.submit');
    Route::post('/logout', 'Auth\AdminLoginController@logout')->name('admin.logout');

    Route::get('/register', 'Auth\AdminRegisterController@showRegistrationForm')->name('admin.register');
    Route::post('/register', 'Auth\AdminRegisterController@register')->name('admin.register.submit');

    Route::get('/', 'AdminController@index')->name('admin.dashboard');
    Route::get('/manage/users', 'ManageUsersController@showAllUsers')->name('admin.manage.users');
    Route::get('/manage/users/{id}', 'ManageUsersController@getUserDetail')->name('admin.manage.users.detail');
    Route::get('/manage/users/{id}/delete', 'ManageUsersController@deleteUser')->name('admin.manage.users.delete');

    Route::get('/manage/srs', 'ManageSrsController@showAllSrs')->name('admin.manage.srs');
    Route::get('/manage/srs/{id}', 'ManageSrsController@getSrDetail')->name('admin.manage.srs.detail');
    Route::get('/manage/srs/{id}/delete', 'ManageSrsController@deleteSr')->name('admin.manage.srs.delete');

    Route::get('/manage/kss', 'ManageKssController@showAllKss')->name('admin.manage.kss');
    Route::get('/manage/kss/{id}', 'ManageKssController@getKsDetail')->name('admin.manage.kss.detail')->where('id', '[0-9]+');
    Route::get('/manage/kss/{id}/delete', 'ManageKssController@deleteKs')->name('admin.manage.kss.delete');
    Route::get('/manage/kss/{id}/edit', 'ManageKssController@editKs')->name('admin.manage.kss.edit');
    Route::post('/manage/kss/{id}/edit', 'ManageKssController@saveEditKs')->name('admin.manage.kss.save-edit');
    Route::get('/manage/kss/upload', 'ManageKssController@showUploadForm')->name('admin.manage.kss.upload');
    Route::post('/manage/kss/upload', 'ManageKssController@uploadKs')->name('admin.manage.kss.upload.submit');

    Route::post('/manage/genres/add', 'ManageKssController@addGenre')->name('admin.manage.genre.add');
    Route::post('/manage/artist/add', 'ManageKssController@addArtist')->name('admin.manage.artist.add');

    Route::get('/manage/reports', 'ManageReportController@index')->name('admin.manage.reports');

    Route::get('/profile', 'AdminProfileController@profile')->name('admin.profile');
    Route::post('/profile/save', 'AdminProfileController@save')->name('admin.profile.save');
});

Route::get('/test', function(){
    return view('test');
});