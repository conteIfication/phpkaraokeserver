@extends('layouts.admin-app')

@section('content')

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="#">
                        <em class="fa fa-home"></em>
                    </a></li>
                <li class="active">Statistic</li>
            </ol>
        </div><!--/.row-->

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dashboard</h1>
            </div>
        </div><!--/.row-->

        <div class="panel panel-container">
            <div class="row">
                <div class="col-xs-6 col-md-3 col-lg-3 no-padding">
                    <div class="panel panel-teal panel-widget border-right">
                        <div class="row no-padding"><em class="fa fa-xl fa-shopping-cart color-blue"></em>
                            <div class="large">120</div>
                            <div class="text-muted">Shared Records</div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 col-md-3 col-lg-3 no-padding">
                    <div class="panel panel-blue panel-widget border-right">
                        <div class="row no-padding"><em class="fa fa-xl fa-comments color-orange"></em>
                            <div class="large">52</div>
                            <div class="text-muted">Uploaded KaraokeSongs</div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 col-md-3 col-lg-3 no-padding">
                    <div class="panel panel-orange panel-widget border-right">
                        <div class="row no-padding"><em class="fa fa-xl fa-users color-teal"></em>
                            <div class="large">24</div>
                            <div class="text-muted">New Users</div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 col-md-3 col-lg-3 no-padding">
                    <div class="panel panel-red panel-widget ">
                        <div class="row no-padding"><em class="fa fa-xl fa-search color-red"></em>
                            <div class="large">250</div>
                            <div class="text-muted">Report & Feedback</div>
                        </div>
                    </div>
                </div>
            </div><!--/.row-->
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Site Traffic Overview
                        <ul class="pull-right panel-settings panel-button-tab-right">
                            <li class="dropdown"><a class="pull-right dropdown-toggle" data-toggle="dropdown" href="#">
                                    <em class="fa fa-cogs"></em>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <ul class="dropdown-settings">
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 1
                                                </a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 2
                                                </a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 3
                                                </a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <span class="pull-right clickable panel-toggle panel-button-tab-left"><em class="fa fa-toggle-up"></em></span></div>
                    <div class="panel-body">
                        <div class="canvas-wrapper">
                            <canvas class="main-chart" id="line-chart" height="200" width="600"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/.row-->

        <div class="row">
            <div class="col-xs-6 col-md-3">
                <div class="panel panel-default">
                    <div class="panel-body easypiechart-panel">
                        <h4>Shared Records</h4>
                        <div class="easypiechart" id="easypiechart-blue" data-percent="92" ><span class="percent">92%</span></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-md-3">
                <div class="panel panel-default">
                    <div class="panel-body easypiechart-panel">
                        <h4>Uploaded KaraokeSongs</h4>
                        <div class="easypiechart" id="easypiechart-orange" data-percent="65" ><span class="percent">65%</span></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-md-3">
                <div class="panel panel-default">
                    <div class="panel-body easypiechart-panel">
                        <h4>New Users</h4>
                        <div class="easypiechart" id="easypiechart-teal" data-percent="56" ><span class="percent">56%</span></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-md-3">
                <div class="panel panel-default">
                    <div class="panel-body easypiechart-panel">
                        <h4>Report and Feedback</h4>
                        <div class="easypiechart" id="easypiechart-red" data-percent="27" ><span class="percent">27%</span></div>
                    </div>
                </div>
            </div>
        </div><!--/.row-->

        <div class="row">



            <div class="col-md-12">
                <div class="panel panel-default ">
                    <div class="panel-heading">
                        Timeline
                        <ul class="pull-right panel-settings panel-button-tab-right">
                            <li class="dropdown"><a class="pull-right dropdown-toggle" data-toggle="dropdown" href="#">
                                    <em class="fa fa-cogs"></em>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <ul class="dropdown-settings">
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 1
                                                </a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 2
                                                </a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">
                                                    <em class="fa fa-cog"></em> Settings 3
                                                </a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <span class="pull-right clickable panel-toggle panel-button-tab-left"><em class="fa fa-toggle-up"></em></span></div>
                    <div class="panel-body timeline-container">
                        <ul class="timeline">
                            <li>
                                <div class="timeline-badge"><em class="glyphicon glyphicon-pushpin"></em></div>
                                <div class="timeline-panel">
                                    <div class="timeline-heading">
                                        <h4 class="timeline-title">Lorem ipsum dolor sit amet</h4>
                                    </div>
                                    <div class="timeline-body">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at sodales nisl. Donec malesuada orci ornare risus finibus feugiat.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="timeline-badge primary"><em class="glyphicon glyphicon-link"></em></div>
                                <div class="timeline-panel">
                                    <div class="timeline-heading">
                                        <h4 class="timeline-title">Lorem ipsum dolor sit amet</h4>
                                    </div>
                                    <div class="timeline-body">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="timeline-badge"><em class="glyphicon glyphicon-camera"></em></div>
                                <div class="timeline-panel">
                                    <div class="timeline-heading">
                                        <h4 class="timeline-title">Lorem ipsum dolor sit amet</h4>
                                    </div>
                                    <div class="timeline-body">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at sodales nisl. Donec malesuada orci ornare risus finibus feugiat.</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="timeline-badge"><em class="glyphicon glyphicon-paperclip"></em></div>
                                <div class="timeline-panel">
                                    <div class="timeline-heading">
                                        <h4 class="timeline-title">Lorem ipsum dolor sit amet</h4>
                                    </div>
                                    <div class="timeline-body">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div><!--/.col-->
            <div class="col-sm-12">
                <p class="back-link">Lumino Theme by <a href="https://www.medialoot.com">Medialoot</a></p>
            </div>
        </div><!--/.row-->
    </div>


    <script>
        window.onload = function () {
            var chart1 = document.getElementById("line-chart").getContext("2d");
            window.myLine = new Chart(chart1).Line(lineChartData, {
                responsive: true,
                scaleLineColor: "rgba(0,0,0,.2)",
                scaleGridLineColor: "rgba(0,0,0,.05)",
                scaleFontColor: "#c5c7cc"
            });
        };
    </script>
@endsection
