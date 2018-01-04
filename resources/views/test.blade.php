<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>Document</title>
    <script src="{{ asset('js/jquery-3.2.1.min.js') }}"></script>
</head>
<body>

<h2>Hello Yakirz</h2>
<div class="container">
    <form action="{{ route('admin.manage.kss.upload.submit') }}" method="post" enctype="multipart/form-data">
        <label>Select image to upload:</label>
        <input type="file" name="file2" id="file">
        <input type="submit" value="Upload" name="submit">
        <input type="hidden" value="{{ csrf_token() }}" name="_token">
    </form>
</div>

<script>
    var f;
    $(document).ready(function () {
        $('input[name="file2"]').on('change', function (e) {
            f = e.target.files;
        })
    });

    function submitBeat() {

        // Create an FormData object
        var data = new FormData();

        $.each(f, function(key, value)
        {
            data.append(key, value);
        });

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/manage/kss/upload",
            data: data,
            processData: false,
            contentType: false,
            headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
</script>

</body>
</html>