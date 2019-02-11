<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ftag Cam</title>
    <!-- Bootstrap Core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="static/css/simple-sidebar.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="/static/js/jsmpeg.js"></script>
</head>
<body>
<div id="wrapper">
    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <canvas id="videoCanvas1" width="640" height="480">
                <p>
                    Please use a browser that supports the Canvas Element, like
                    <a href="http://www.google.com/chrome">Chrome</a>,
                    <a href="http://www.mozilla.com/firefox/">Firefox</a>,
                    <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
                </p>
            </canvas>
            <canvas id="videoCanvas2" width="640" height="480">
                <p>
                    Please use a browser that supports the Canvas Element, like
                    <a href="http://www.google.com/chrome">Chrome</a>,
                    <a href="http://www.mozilla.com/firefox/">Firefox</a>,
                    <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
                </p>
            </canvas>
            <canvas id="videoCanvas3" width="640" height="480">
                    <p>
                        Please use a browser that supports the Canvas Element, like
                        <a href="http://www.google.com/chrome">Chrome</a>,
                        <a href="http://www.mozilla.com/firefox/">Firefox</a>,
                        <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
                    </p>
                </canvas>
                <script type="text/javascript">
                    // Setup the WebSocket connection and start the player
                    function absoluteURL(s) {
                        var l = window.location;
                        return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
                    }

                    play(absoluteURL('/stream/output/1'), 'videoCanvas1');
                    // play('ws://127.0.0.1:8181/stream/output/2', 'videoCanvas2');
                    // play('ws://127.0.0.1:8181/stream/output/3', 'videoCanvas3');
                    //play('ws://131.130.43.176:8010/stream/output/1','videoCanvas1');
                    //play('ws://131.130.43.176:8010/stream/output/2','videoCanvas2');
                    //play('ws://131.130.43.176:8010/stream/output/3','videoCanvas3');
                    function play(socketURL, canvasName){
                        // var client = new ReconnectingWebSocket(socketURL, null, {debug: false, reconnectInterval: 100, binaryType:'arraybuffer'});
                        var canvas = document.getElementById(canvasName);
                        var player = new JSMpeg.Player(socketURL, {canvas:canvas});
                    }
                </script>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="static/js/jquery-3.1.0.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="static/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>
</body>
</html>
