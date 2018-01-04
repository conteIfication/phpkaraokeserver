@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li>Manage</li>
                <li class="active">Users</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage Users</h1>
            </div>
        </div><!--/.row-->

        <div class="panel panel-container">
            <div class="bootstrap-table">

                <div class="fixed-table-toolbar">
                    <div class="columns btn-group pull-right">
                        <button onclick="searchUser()" id="btn_search_user" class="btn btn-default" type="button" name="search"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                    <div class="pull-right search"><input id="ip_search_user" class="form-control" type="text" placeholder="Search ID"></div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">All Users</div>
                    <div class="panel-body btn-margins">
                        <div class="col-md-12">
                            <table class="table table-hover" id="tb_all_users">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Avatar</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Gender</th>
                                    <th>Birthday</th>
                                    <th>Function</th>
                                </tr>
                                </thead>
                                <tbody>
                                @foreach( $users as $idx => $user )
                                    <tr>
                                        <td>{{ $user->id }}</td>
                                        <td>
                                            <img class="img-rounded" width="50px"
                                                 src="{{ $user->avatar != null ? ('/storage/photos/' . $user->avatar) : '' }}"/>
                                        </td>
                                        <td>{{ $user->name }}</td>
                                        <td>{{ $user->email }}</td>
                                        <td>{{ $user->gender }}</td>
                                        <td>{{ $user->birthday }}</td>
                                        <td>
                                            <button class="btn btn-danger" data-toggle="modal" data-target="#md_delete_user"
                                                onclick="openMdDeleteUser({{ $user->id }})">Delete</button>
                                            <button class="btn btn-user-detail" data-toggle="modal" data-target="#md_user_detail"
                                                onclick="openMdUserDetail({{ $user->id }})"><em class="fa fa-info-circle"></em></button>
                                        </td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>

                        <div class="fixed-table-pagination">
                            <div class="pull-right pagination">
                                {{ $users->links() }}
                            </div>
                        </div>

                        <!-- Modal User Detail-->
                        <div id="md_user_detail" class="modal fade" role="dialog">
                            <div class="modal-dialog">
                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">User detail</h4>
                                    </div>
                                    <div class="modal-body" style="display: -webkit-box">
                                        <div class="col-md-4">
                                            <img id="user_avatar" src="" alt="Avatar" style="max-width: 100%">
                                        </div>
                                        <div class="col-md-8">
                                            <table>
                                                <tr>
                                                    <th>ID</th>
                                                    <td id="user_id"></td>
                                                </tr>
                                                <tr>
                                                    <th>Email</th>
                                                    <td id="user_email"></td>
                                                </tr>
                                                <tr>
                                                    <th>Birthday</th>
                                                    <td id="user_birthday"></td>
                                                </tr><tr>
                                                    <th>Phone</th>
                                                    <td id="user_phone_no"></td>
                                                </tr>
                                                <tr>
                                                    <th>Gender</th>
                                                    <td id="user_gender"></td>
                                                </tr>
                                                <tr>
                                                    <th>Introduce</th>
                                                    <td id="user_introduce"></td>
                                                </tr>
                                            </table>

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal Delete User-->
                        <div id="md_delete_user" class="modal fade" role="dialog">
                            <div class="modal-dialog">
                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Are you sure you want to delete this user?</h4>
                                    </div>
                                    {{--<div class="modal-body" style="display: -webkit-box">
                                        <h4>Are you sure you want to delete this user?</h4>
                                    </div>--}}
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal" id="btn_delete_user">Delete</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- script -->
    <script>
        function searchUser() {
            var input = $('#ip_search_user').val();

            if (input != '' && !isNaN(input)){
                $.get('/admin/manage/users/' + input, function (data, status) {
                    if (data == ''){
                        $('#ip_search_user').val('');
                        return;
                    }
                    $('#tb_all_users tbody tr').remove();
                    $('ul.pagination').remove();

                    var txt = '<tr>' +
                        '<td>' + data.id + '</td>' +
                        '<td><img class="img-rounded" width="50px" src="' + (data.avatar != null ? '/storage/photos/' + data.avatar : '') + '"/></td>' +
                        '<td>'+data.name+'</td>' +
                        '<td>'+data.email+'</td>' +
                        '<td>'+data.gender+'</td>' +
                        '<td>'+data.birthday+'</td>' +
                        '<td><button class="btn btn-danger" data-toggle="modal" data-target="#md_delete_user" ' +
                        'onclick="openMdDeleteUser('+data.id+')">Delete</button>' +
                        '<button class="btn btn-user-detail" data-toggle="modal" data-target="#md_user_detail" '+
                        'onclick="openMdUserDetail('+data.id+')"><em class="fa fa-info-circle"></em></button></td></tr>';
                    $('#tb_all_users tbody').append(txt);
                });
            }else {
                $('#ip_search_user').val('');
            }
        }

        function openMdUserDetail( uid ) {
            $.get('/admin/manage/users/' + uid, function (data, status) {
                console.log(data);
                $('#user_id').text( data.id );
                $('#user_email').text( data.email );
                $('#user_birthday').text( data.birthday );
                $('#user_phone_no').text( data.phone_no );
                $('#user_gender').text( data.gender );
                $('#user_introduce').text( data.introduce );
                if (data.avatar != null){
                    $('#user_avatar').attr('src', '/storage/photos/' + data.avatar);
                }else {
                    $('#user_avatar').removeAttr('src');
                }
            });
        }

        function openMdDeleteUser( uid ) {
            $('#btn_delete_user').off().click(function () {
                $.get('/admin/manage/users/' + uid + '/delete', function (data, status) {
                    if (data == 1) {
                        location.reload();
                    }else {
                        console.log('delete fail');
                    }
                });
            });
        }

    </script>

@endsection
