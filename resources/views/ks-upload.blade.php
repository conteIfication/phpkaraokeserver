@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li>Manage</li>
                <li >KaraokeSongs</li>
                <li class="active">Upload</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">KaraokeSongs</h1>
            </div>
        </div><!--/.row-->

        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">Upload</div>
                    <div class="panel-body">

                        <form id="formUploadKs" class="form-horizontal row-border"
                              enctype="multipart/form-data" method="post"
                              action="{{ route('admin.manage.kss.upload.submit') }}">
                            <div class="form-group">
                                <label class="col-md-2 control-label">Song name</label>
                                <div class="col-md-10">
                                    <input type="text" name="ks_name" class="form-control">
                                    {{ csrf_field() }}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Genre</label>
                                <div class="col-xs-7">
                                    @foreach($genres as $genre)
                                        <input type="checkbox" value="{{ $genre->id }}"
                                               id="cb_genre_{{ $genre->id }}"  name="cb_genres[]">
                                        <label for="cb_genre_{{ $genre->id }}">{{ $genre->name }}</label>
                                    @endforeach
                                </div>
                                <div class="col-xs-3">
                                    <button class="btn btn-default" type="button"
                                        data-toggle="modal" data-target="#md_add_genre">
                                        <em class="fa fa-plus"></em></button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Artist</label>
                                <div class="col-xs-7">
                                    @foreach($artists as $artist)
                                        <input type="checkbox" value="{{ $artist->id }}"
                                               id="cb_artist_{{ $artist->id }}" name="cb_artists[]">
                                        <label for="cb_artist_{{ $artist->id }}">{{ $artist->name }}</label>
                                    @endforeach
                                </div>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-default"
                                    data-toggle="modal" data-target="#md_add_artist"><em class="fa fa-plus"></em></button></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Year</label>
                                <div class="col-md-3">
                                    <select name="ks_year" id="ks_year" class="form-control">
                                        @for($k = 1970; $k < 2018; $k++)
                                            <option value="{{ $k }}">{{ $k }}</option>
                                        @endfor
                                    </select>
                                    <span class="help-block"></span></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Beat</label>
                                <div class="col-md-10">
                                    <input type="file" name="beat" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Lyric</label>
                                <div class="col-md-10">
                                    <input type="file" name="lyric" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Image</label>
                                <div class="col-md-10">
                                    <input type="file" name="image" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <input type="submit" value="Submit" class="btn btn-primary" >
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal Add Genre-->
    <div id="md_add_genre" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add genre</h4>
                </div>
                <div class="modal-body" style="display: -webkit-box">
                    <form id="formAddGenre" class="form-horizontal row-border"
                        method="post" action="{{ route('admin.manage.genre.add') }}">
                        {{ csrf_field() }}
                        <div class="form-group">
                            <label class="col-md-4 control-label">Genre name</label>
                            <div class="col-md-8">
                                <input type="text" name="genre_name" class="form-control">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-success"
                            data-dismiss="modal" id="btn_add_genre"
                        onclick="addGenre()">Add</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Add Artist-->
    <div id="md_add_artist" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add artist</h4>
                </div>
                <div class="modal-body" style="display: -webkit-box">
                    <form id="formAddArtist" class="form-horizontal row-border"
                        method="post" action="{{ route('admin.manage.artist.add') }}">
                        {{ csrf_field() }}
                        <div class="form-group">
                            <label class="col-md-4 control-label">Artist name</label>
                            <div class="col-md-8">
                                <input type="text" name="artist_name" class="form-control">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-success"
                            data-dismiss="modal" id="btn_add_artist"
                        onclick="addArtist()">Add</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function addGenre() {
            $('#formAddGenre').submit();
        }
        function addArtist() {
            $('#formAddArtist').submit();
        }
    </script>

@endsection
