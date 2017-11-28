<?php

    $data = DB::table('galary')->get();
    //echo $data;
?>
{{ $galaries }}

@section('hihi')
    <form >
        {{ csrf_field() }}
    </form>

@show
