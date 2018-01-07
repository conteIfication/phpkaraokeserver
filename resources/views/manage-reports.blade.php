@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li>Manage</li>
                <li class="active">Reports</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Manage Reports</h1>
            </div>
        </div><!--/.row-->

        <div class="panel panel-container">
            <div class="bootstrap-table">

                {{--<div class="fixed-table-toolbar">
                    <div class="columns btn-group pull-right">
                        <button onclick="searchUser()" id="btn_search_user" class="btn btn-default" type="button" name="search"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                    <div class="pull-right search"><input id="ip_search_user" class="form-control" type="text" placeholder="Search ID">
                    </div>
                </div>--}}
                <div class="panel panel-default">
                    <div class="panel-heading">All Reports</div>
                    <div class="panel-body btn-margins">
                        <div class="col-md-12">
                            <table class="table table-hover" id="tb_all_reports">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Song</th>
                                    <th>Subject</th>
                                    <th>User</th>
                                    <th>Created at</th>
                                </tr>
                                </thead>
                                <tbody>
                                @foreach($reports as $report)
                                    <tr>
                                        <td>@if($report->sr_id != null)
                                            sr-{{ $report->sr_id }}
                                            @else
                                                ks-{{ $report->kar_id }}
                                            @endif</td>
                                        <td>@if($report->sr_id != null)
                                            {{ $report->sharedrecord->karaoke->name }}
                                        @else
                                            {{ $report->karaoke->name }}
                                        @endif</td>
                                        <td>@if($report->sr_id != null)
                                                @if($report->subject == 0)
                                                    Wrong lyrics
                                                @elseif($report->subject == 1)
                                                    Low sound
                                                @elseif($report->subject == 2)
                                                    Asynchronous lyrics and music
                                                @endif
                                            @else
                                                @if($report->subject == 0)
                                                    Bad recording
                                                @elseif($report->subject == 1)
                                                    Lots of noise
                                                @elseif($report->subject == 2)
                                                    Asynchronous lyrics and music
                                                @endif
                                            @endif</td>
                                        <td>
                                            <a href="#" data-toggle="modal" data-target="#md_user_detail"
                                               onclick="openMdUserDetail({{ $report->user->id }})">{{ $report->user->name }}</a></td>
                                        <td>{{ $report->created_at }}</td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>

                        <div class="fixed-table-pagination">
                            <div class="pull-right pagination">
                                {{ $reports->links() }}
                            </div>
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

    <!-- script -->
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
    </script>

@endsection
