<!DOCTYPE html>
<html lang="{{ app()->getLocale() }}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <title>{{ config('app.name', 'Laravel') }}</title>

    <!-- Styles -->
    <link href="{{ asset('css/app.css') }}" rel="stylesheet">
    <link href="{{ asset('css/bootstrap.min.css') }}" rel="stylesheet">
    <link href="{{ asset('css/font-awesome.min.css') }}" rel="stylesheet">
    {{--<link href="{{ asset('css/datepicker3.css') }}" rel="stylesheet">--}}
    <link href="{{ asset('css/styles.css') }}" rel="stylesheet">
    <script src="{{ asset('js/jquery-1.11.1.min.js') }}"></script>
    <!--Custom Font-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

</head>
<body>
<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">

    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse"><span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="{{ route('admin.dashboard') }}"><span>Karaoke</span>Admin</a>
        </div>
    </div><!-- /.container-fluid -->
</nav>
<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">

    <ul class="nav menu">
        @guest('admin')
            <li><a href="{{ route('admin.login') }}">Login</a></li>
            <li><a href="{{ route('admin.register') }}">Register</a></li>
        @else
            <li><a href="{{ route('admin.dashboard') }}"><em class="fa fa-dashboard"></em> Home</a></li>
            <li><a href="{{ route('admin.manage.users') }}"><span class="fa fa-users"></span> Users</a></li>
            <li><a href="{{ route('admin.manage.srs') }}"><span class="fa fa-microphone"></span> Shared Records</a></li>
            <li><a href="{{ route('admin.manage.kss') }}"><span class="fa fa-play-circle"></span> Karaoke Songs</a></li>
            <li><a href="{{ route('admin.manage.reports') }}"><span class="fa fa-archive"></span> Reports</a></li>
            <li><a href="{{ route('admin.profile') }}"><em class="fa fa-user"></em> Profile</a></li>
            {{--<li><a href="#"><em class="fa fa-bell-o"></em> Annoucement</a></li>--}}
            <li>
                <a href="{{ route('admin.logout') }}"
                   onclick="event.preventDefault();document.getElementById('logout-form').submit();"><em class="fa fa-power-off"></em> Logout</a>
                <form id="logout-form" action="{{ route('admin.logout') }}" method="POST" style="display: none;">
                    {{ csrf_field() }}
                </form>
            </li>
        @endguest

    </ul>
</div><!--/.sidebar-->
@yield('content')

<!-- Scripts -->
{{--<script src="{{ asset('js/app.js') }}"></script>--}}

<script src="{{ asset('js/bootstrap.min.js') }}"></script>
<script src="{{ asset('js/chart.min.js') }}"></script>
<script src="{{ asset('js/chart-data.js') }}"></script>
<script src="{{ asset('js/easypiechart.js') }}"></script>
<script src="{{ asset('js/easypiechart-data.js') }}"></script>
<script src="{{ asset('js/bootstrap-datepicker.js') }}"></script>
<script src="{{ asset('js/custom.js') }}"></script>


</body>
</html>
