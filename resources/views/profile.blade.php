@extends('layouts.admin-app')

@section('content')
    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a>
                </li>
                <li class="active">Profile</li>
            </ol>
        </div>
        <!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Profile</h1>
            </div>
        </div>
        <!--/.row-->
        <div>
            <div class="col-md-4">
                <div class="panel-layout">
                    <div class="panel-box">
                        <div class="panel-content image-box">
                            <div class="ribbon"></div>
                            <div class="image-content font-white">
                                <div class="meta-box meta-box-bottom text-center">
                                    <img src="{{ asset('store/avatar.png') }}" width="140"
                                         alt="" class="meta-image img-bordered img-circle" style="display: inline-block">
                                    <h3 class="meta-heading">{{ Auth::guard('admin')->user()->name }}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/.row-->
        <div class="col-md-8">
            <div class="example-box-wrapper">
                <div class="tab-content">
                    <div class="tab-pane pad0A fade active in" id="tab-example-4">
                        <div class="content-box">
                            <form class="form-horizontal pad15L pad15R bordered-row" method="post"
                                action="{{ route('admin.profile.save') }}">
                                {{ csrf_field() }}
                                <input name="id" value="{{ $admin->id }}" type="hidden">
                                <div class="form-group remove-border">
                                    <label class="col-sm-3 control-label">Name:</label>
                                    <div class="col-sm-6">
                                        <input type="text" value="{{ $admin->name }}" class="form-control"
                                               name="name" placeholder="Name..."></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Email:</label>
                                    <div class="col-sm-6"><input type="email" class="form-control"  placeholder="Email address..." disabled value="{{ $admin->email  }}"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Birthday:</label>
                                    <div class="col-sm-6"><input type="date" class="form-control" name="birthday" placeholder="Birthday..." value="{{ $admin->birthday }}"></div>
                                </div>
                                {{--<div class="form-group">
                                    <label class="col-sm-3 control-label">About me:</label>
                                    <div class="col-sm-6"><textarea name="" rows="3" class="form-control textarea-autosize" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 78px;"></textarea></div>
                                </div>--}}
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"></label>
                                    <div class="col-sm-6"><button type="submit" class="btn btn-info">Save</button></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.row-->
            <!--/.row-->
        </div>
@endsection
