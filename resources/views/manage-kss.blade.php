@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li>Manage</li>
                <li class="active">KaraokeSongs</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage KaraokeSongs</h1>
            </div>
        </div><!--/.row-->

        <div class="panel panel-container">
            <div class="bootstrap-table">

                <div class="fixed-table-toolbar">
                    <div class="columns btn-group pull-right">
                        <button onclick="searchKs()" class="btn btn-default" type="button" name="search" title="Search"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                    <div class="pull-right search"><input id="ip_search_ks" class="form-control" type="text" placeholder="Search ID"></div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">All KaraokeSongs</div>
                    <div class="panel-body btn-margins">
                        <div class="col-md-12">
                            <table class="table table-hover" id="tb_all_kss">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Artist</th>
                                    <th>Genre</th>
                                    <th>View No</th>
                                    <th>Created at</th>
                                    <th>Cover</th>
                                    <th>Year</th>
                                    <th>Function</th>
                                </tr>
                                </thead>
                                <tbody>
                                @foreach($kss as $ks)
                                    <tr>
                                        <td>{{ $ks->id }}</td>
                                        <td>{{ $ks->name }}</td>
                                        <td>{{ $ks->artist }}</td>
                                        <td>{{ $ks->genre }}</td>
                                        <td>{{ $ks->view_no }}</td>
                                        <td>{{ $ks->created_at }}</td>
                                        <td>
                                            <img class="img-rounded" width="50px"
                                                 src="{{ $ks->image != null ? ('/storage/songs/' . substr($ks->beat, 0, strlen($ks->beat) - 4) . '/' . $ks->image) : '' }}"/>
                                        </td>
                                        <td>{{ $ks->year }}</td>
                                        <td>
                                            <a class="btn btn-info" href="{{ route('admin.manage.kss.edit', ['id' => $ks->id]) }}">Edit</a>
                                            <button class="btn btn-danger" data-toggle="modal" data-target="#md_delete_ks"
                                                    onclick="openMdDeleteKs({{ $ks->id }})"><em class="fa fa-trash-o"></em></button>
                                            <button class="btn" data-toggle="modal" data-target="#md_ks_detail"
                                                    onclick="openMdKsDetail({{ $ks->id }})"><em class="fa fa-info-circle"></em></button>
                                        </td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>
                        <div class="fixed-table-pagination">
                            <div class="pull-right pagination">
                                {{ $kss->links() }}
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-footer">
                        <h3>Add a new song</h3>
                        <a class="btn btn-success" href="{{ route('admin.manage.kss.upload') }}">Upload</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Ks Detail-->
        <div id="md_ks_detail" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Karaoke detail</h4>
                    </div>
                    <div class="modal-body" style="display: -webkit-box">
                        <div class="col-md-4">
                            <img id="ks_image" src="" alt="Cover" style="max-width: 100%">
                        </div>
                        <div class="col-md-8">
                            <table>
                                <tr>
                                    <th>ID</th>
                                    <td id="ks_id"></td>
                                </tr>
                                <tr>
                                    <th>Song Name</th>
                                    <td id="ks_name"></td>
                                </tr>
                                <tr>
                                    <th>Artist</th>
                                    <td id="ks_artist"></td>
                                </tr>
                                <tr>
                                    <th>Genre</th>
                                    <td id="ks_genre"></td>
                                </tr>
                                <tr>
                                    <th>Beat</th>
                                    <td id="ks_beat"></td>
                                </tr>
                                <tr>
                                    <th>Lyric</th>
                                    <td id="ks_lyric"></td>
                                </tr>
                                <tr>
                                    <th>Year</th>
                                    <td id="ks_year"></td>
                                </tr>
                                <tr>
                                    <th>View No</th>
                                    <td id="ks_view_no"></td>
                                </tr>
                                <tr>
                                    <th>Created At</th>
                                    <td id="ks_created_at"></td>
                                </tr>
                            </table>

                            <audio id="audio_player" controls>
                                <source src="" type="video/mpeg">
                            </audio>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="btn_close_md_ksdetail">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Delete Ks-->
        <div id="md_delete_ks" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Are you sure you want to delete this karaoke song?</h4>
                    </div>
                    {{--<div class="modal-body" style="display: -webkit-box">
                        <h4>Are you sure you want to delete this user?</h4>
                    </div>--}}
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal" id="btn_delete_ks">Delete</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <script>
        function openMdKsDetail( id ) {
            $.get('/admin/manage/kss/' + id, function (data, status) {
                console.log(data);

                $('#ks_id').text( data.id );
                $('#ks_artist').text( data.artist );
                $('#ks_genre').text( data.genre );
                $('#ks_beat').text( data.beat );
                $('#ks_lyric').text( data.lyric );
                $('#ks_created_at').text( data.created_at );
                $('#ks_name').text( data.name );
                $('#ks_view_no').text( data.view_no );
                $('#ks_year').text( data.year );

                var folderPath = data.beat.substring(0, data.beat.length - 4);
                if (data.image != null){
                    $('#ks_image').attr('src', '/storage/songs/' + folderPath + '/' + data.image);
                }else {
                    $('#ks_image').removeAttr('src');
                }
                $('#audio_player').css('display', 'block').attr('src', '/storage/songs/' + folderPath + '/' + data.beat).load();

                //close button
                $('#btn_close_md_ksdetail').off().on('click', function () {
                    var player = document.getElementById('audio_player');
                    if (!player.paused){
                        player.pause();
                    }
                });
            });
        }
        function openMdDeleteKs( id ) {
            $('#btn_delete_ks').off().click(function () {
                $.get('/admin/manage/kss/' + id + '/delete', function (data, status) {
                    if (data == 1) {
                        location.reload();
                    }else {
                        console.log('delete fail');
                    }
                });
            });
        }
        function searchKs() {
            var input = $('#ip_search_ks').val();

            if (input != '' && !isNaN(input)){
                $.get('/admin/manage/kss/' + input, function (data, status) {
                    console.log(data);
                    if (data == ''){
                        $('#ip_search_ks').val('');
                        return;
                    }
                    $('#tb_all_kss tbody tr').remove();
                    $('ul.pagination').remove();

                    var folderPath = data.beat.substring(0, data.beat.length - 4);
                    var txt = '<tr>' +
                        '<td>' + data.id + '</td>' +
                        '<td>' + data.name + '</td>'+
                        '<td>'+data.artist+'</td>' +
                        '<td>'+data.genre+'</td>' +
                        '<td>'+data.view_no+'</td>' +
                        '<td>'+data.created_at+'</td>' +
                        '<td><img class="img-rounded" width="50px" src="' + '/storage/songs/' + folderPath + '/' + data.image + '"/></td>' +
                        '<td>'+data.year+'</td>' +
                        '<td> <a href="#" data-toggle="modal" data-target="#md_ks_detail"onclick="openMdKsDetail('+data.id+')"></a></td>' +
                        '<td><button class="btn btn-info">Edit</button>' +
                        '<button class="btn btn-danger"><em class="fa fa-trash-o"></em></button>' +
                        '<button class="btn" data-toggle="modal" data-target="#md_ks_detail"onclick="openMdKsDetail('+data.id+')"><em class="fa fa-info-circle"></em></button>' +
                        '</td></tr>';
                    $('#tb_all_kss tbody').append(txt);
                });
            }else {
                $('#ip_search_ks').val('');
            }
        }

    </script>

@endsection
