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
                <li class="active">Edit</li>
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
                    <div class="panel-heading clearfix">Edit</div>
                    <div class="panel-body">

                        <form id="formUploadKs" class="form-horizontal row-border"
                              enctype="multipart/form-data" method="post" action="{{ route('admin.manage.kss.save-edit', ['id' => $ks->id]) }}">
                            <div class="form-group">
                                <label class="col-md-2 control-label">Song name</label>
                                <div class="col-md-10">
                                    <input type="text" name="ks_name" class="form-control" value="{{ $ks->name }}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Genre</label>
                                <div class="col-xs-3">
                                    <select name="ks_genre" id="ks_genre" class="form-control">
                                        @foreach($genres as $genre)
                                            <option value="{{ $genre->id }}">{{ $genre->name }}</option>
                                        @endforeach
                                    </select>
                                </div>
                                <div class="col-xs-3"><button class="btn btn-default"><em class="fa fa-plus"></em></button></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Artist</label>
                                <div class="col-xs-3">
                                    <select name="ks_artist" id="ks_artist" class="form-control">
                                        @foreach($artists as $artist)
                                            <option value="{{ $artist->id }}">{{ $artist->name }}</option>
                                        @endforeach
                                    </select>
                                </div>
                                <div class="col-xs-3"><button class="btn btn-default"><em class="fa fa-plus"></em></button></div>
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
                                    <input type="file" name="beat" class="form-control" disabled>
                                    <input type="hidden" value="{{ csrf_token() }}" name="_token">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Lyric</label>
                                <div class="col-md-10">
                                    <input type="file" name="lyric" class="form-control" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">Image</label>
                                <div class="col-md-10">
                                    <input type="file" name="image" class="form-control" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <input type="submit" value="Save" class="btn btn-success" >
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>

    </script>

@endsection
