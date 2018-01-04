<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;

class ManageUsersController extends Controller
{
    //
    public function showAllUsers() {
        $users = User::paginate(10);

        return view('manage-users', ['users' => $users]);
    }
    public function getUserDetail($id){
        return User::find($id);
    }
    public function deleteUser($id) {
        if (User::where('id', $id)->delete())
            return 1;
        return 0;
    }
}
