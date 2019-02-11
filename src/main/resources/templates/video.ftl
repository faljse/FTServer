<script type="text/javascript" src="${publicURL}/static/js/jsmpeg.js"></script>
<style>
.nopadding{padding: 0px}
.nomargin{margin: 0px}
</style>

<div id="wrapper">
    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="nopadding container-fluid">
            <div style="float:left">
	            <div style="float:left;background-color:blue;">
	                <canvas id="videoCanvas1" width="640" height="480">
	                    <p>
	                        Please use a browser that supports the Canvas Element, like
	                        <a href="http://www.google.com/chrome">Chrome</a>,
	                        <a href="http://www.mozilla.com/firefox/">Firefox</a>,
	                        <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
	                    </p>
	                </canvas>
	            </div>
	            <div style="float:left;clear:right;background-color:green;">
	                <canvas id="videoCanvas2" width="640" height="480">
	                    <p>
	                        Please use a browser that supports the Canvas Element, like
	                        <a href="http://www.google.com/chrome">Chrome</a>,
	                        <a href="http://www.mozilla.com/firefox/">Firefox</a>,
	                        <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
	                    </p>
	                </canvas>
	            </div>
	            <div style="float:left;background-color:red;">
	                <canvas id="videoCanvas3" width="640" height="480">
	                    <p>
	                        Please use a browser that supports the Canvas Element, like
	                        <a href="http://www.google.com/chrome">Chrome</a>,
	                        <a href="http://www.mozilla.com/firefox/">Firefox</a>,
	                        <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
	                    </p>
	                </canvas>
	            </div>
	            <div style="float:left;background-color:yellow;">
	                <canvas id="videoCanvas4" width="640" height="480">
	                    <p>
	                        Please use a browser that supports the Canvas Element, like
	                        <a href="http://www.google.com/chrome">Chrome</a>,
	                        <a href="http://www.mozilla.com/firefox/">Firefox</a>,
	                        <a href="http://www.apple.com/safari/">Safari</a> or Internet Explorer 10
	                    </p>
	                </canvas>
	            </div>
	         </div>
        </div>
        <script type="text/javascript">
            $('#page-content-wrapper').css('min-width','1280px');

            // Setup the WebSocket connection and start the player
            function absoluteURL(s) {
                var l = window.location;
                return ((l.protocol === "https:") ? "wss://" : "ws://") + l.host + l.pathname + s;
            }
            play('ws://131.130.43.176:8010/stream/output/1', 'videoCanvas1');
            play('ws://131.130.43.176:8010/stream/output/2', 'videoCanvas2');
            play('ws://131.130.43.176:8010/stream/output/3', 'videoCanvas3');
            play('ws://131.130.43.176:8010/stream/output/4', 'videoCanvas4');
            function play(socketURL, canvasName){
                var canvas = document.getElementById(canvasName);
                var player = new JSMpeg.Player(socketURL, {canvas:canvas});
            }
        </script>
        </div>
        ${statsBytes}
    </div>
    <!-- /#page-content-wrapper -->
</div>
<!-- /#wrapper -->
