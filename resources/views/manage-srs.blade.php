@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li>Manage</li>
                <li class="active">Shared Records</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage Shared Records</h1>
            </div>
        </div><!--/.row-->

        <div class="panel panel-container">
            <div class="bootstrap-table">

                <div class="fixed-table-toolbar">
                    <div class="columns btn-group pull-right">
                        <button onclick="searchSr()" class="btn btn-default" type="button" name="search" title="Search"><i class="glyphicon glyphicon-search "></i></button>
                    </div>
                    <div class="pull-right search"><input id="ip_search_sr" class="form-control" type="text" placeholder="Search ID"></div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">All Records</div>
                    <div class="panel-body btn-margins">
                        <div class="col-md-12">
                            <table class="table table-hover" id="tb_all_srs">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Song Name</th>
                                    <th>KID</th>
                                    <th>Content</th>
                                    <th>Type</th>
                                    <th>Score</th>
                                    <th>Shared At</th>
                                    <th>Owner</th>
                                    <th>Function</th>
                                </tr>
                                </thead>
                                <tbody>
                                @foreach($srs as $sr)
                                    <tr>
                                        <td>{{ $sr->id }}</td>
                                        <td>{{ $sr->karaoke->name }}</td>
                                        <td>{{ $sr->karaoke->id }}</td>
                                        <td>{{ $sr->content }}</td>
                                        <td>{{ $sr->type }}</td>
                                        <td>{{ $sr->score }}</td>
                                        <td>{{ $sr->shared_at }}</td>
                                        <td>
                                            <a href="#" data-toggle="modal" data-target="#md_user_detail"
                                               onclick="openMdUserDetail({{ $sr->user->id }})">{{ $sr->user->name }}</a>
                                        </td>
                                        <td>
                                            <button class="btn btn-danger" data-toggle="modal" data-target="#md_delete_sr"
                                                onclick="openMdDeleteSr({{ $sr->id }})">Delete</button>
                                            <button class="btn" data-toggle="modal" data-target="#md_sr_detail"
                                                onclick="openMdSrDetail({{ $sr->id }})"><em class="fa fa-info-circle"></em></button>
                                        </td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>
                        <div class="fixed-table-pagination">
                            <div class="pull-right pagination">
                                {{ $srs->links() }}
                            </div>
                        </div>
                    </div>
                </div>
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

        <!-- Modal Sr Detail-->
        <div id="md_sr_detail" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Shared record detail</h4>
                    </div>
                    <div class="modal-body" style="display: -webkit-box">
                        <div class="col-md-4">
                            <img id="sr_cover" src="" alt="Cover" style="max-width: 100%">
                        </div>
                        <div class="col-md-8">
                            <table>
                                <tr>
                                    <th>ID</th>
                                    <td id="sr_id"></td>
                                </tr>
                                <tr>
                                    <th>Song Name</th>
                                    <td id="sr_song_name"></td>
                                </tr>
                                <tr>
                                    <th>KID</th>
                                    <td id="sr_kar_id"></td>
                                </tr>
                                <tr>
                                    <th>Content</th>
                                    <td id="sr_content"></td>
                                </tr>
                                <tr>
                                    <th>Score</th>
                                    <td id="sr_score"></td>
                                </tr><tr>
                                    <th>Type</th>
                                    <td id="sr_type"></td>
                                </tr>
                                <tr>
                                    <th>View No</th>
                                    <td id="sr_view_no"></td>
                                </tr>
                                <tr>
                                    <th>Like No</th>
                                    <td id="sr_like_no"></td>
                                </tr>
                                <tr>
                                    <th>Shared At</th>
                                    <td id="sr_shared_at"></td>
                                </tr>
                            </table>

                            <audio id="audio_player" controls>
                                <source src="" type="video/mpeg">
                            </audio>

                            <video id="video_player" width="320" height="240" controls>
                                <source src="" type="video/mp4">
                            </video>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Delete Sr-->
        <div id="md_delete_sr" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Are you sure you want to delete this shared record?</h4>
                    </div>
                    {{--<div class="modal-body" style="display: -webkit-box">
                        <h4>Are you sure you want to delete this user?</h4>
                    </div>--}}
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal" id="btn_delete_sr">Delete</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <script>
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
        function openMdSrDetail( srid ) {
            $.get('/admin/manage/srs/' + srid, function (data, status) {
                console.log(data);
                $('#sr_id').text( data.id );
                $('#sr_song_name').text( data.karaoke.name );
                $('#sr_kar_id').text( data.kar_id );
                $('#sr_content').text( data.content );
                $('#sr_score').text( data.score );
                $('#sr_type').text( data.type );
                $('#sr_view_no').text( data.view_no );
                $('#sr_like_no').text( data.num_likes );
                $('#sr_shared_at').text( data.shared_at );

                if (data.karaoke.image != null){
                    var folderPath = data.karaoke.beat.substring(0, data.karaoke.beat.length - 4);
                    $('#sr_cover').attr('src', '/store/songs/' + folderPath + '/' + data.karaoke.image);
                }else {
                    $('#sr_cover').removeAttr('src');
                }

                if (data.type == 'audio'){
                    $('#video_player').css('display', 'none');
                    $('#audio_player').css('display', 'block').attr('src', '/storage/records/' + data.path).load();
                }else {
                    $('#audio_player').css('display', 'none');
                    $('#video_player').css('display', 'block').attr('src', '/storage/records/' + data.path).load();;
                }
            });
        }
        function openMdDeleteSr( id ) {
            $('#btn_delete_sr').off().click(function () {
                $.get('/admin/manage/srs/' + id + '/delete', function (data, status) {
                    if (data == 1) {
                        location.reload();
                    }else {
                        console.log('delete fail');
                    }
                });
            });
        }
        function searchSr() {
            var input = $('#ip_search_sr').val();

            if (input != '' && !isNaN(input)){
                $.get('/admin/manage/srs/' + input, function (data, status) {
                    if (data == ''){
                        $('#ip_search_sr').val('');
                        return;
                    }
                    $('#tb_all_srs tbody tr').remove();
                    $('ul.pagination').remove();

                    var txt = '<tr>' +
                        '<td>' + data.id + '</td>' +
                        '<td>' + data.karaoke.name + '</td>'+
                        '<td>'+data.kar_id+'</td>' +
                        '<td>'+data.content+'</td>' +
                        '<td>'+data.type+'</td>' +
                        '<td>'+data.score+'</td>' +
                        '<td>'+data.shared_at+'</td>' +
                        '<td> <a href="#" data-toggle="modal" data-target="#md_sr_detail"onclick="openMdSrDetail('+data.id+')"></a></td>' +
                        '<td><button class="btn btn-danger" data-toggle="modal" data-target="#md_delete_sr" ' +
                        'onclick="openMdDeleteSr('+data.id+')">Delete</button>' +
                        '<button class="btn btn-user-detail" data-toggle="modal" data-target="#md_sr_detail" '+
                        'onclick="openMdSrDetail('+data.id+')"><em class="fa fa-info-circle"></em></button></td></tr>';
                    $('#tb_all_srs tbody').append(txt);
                });
            }else {
                $('#ip_search_sr').val('');
            }
        }
    </script>


@endsection
