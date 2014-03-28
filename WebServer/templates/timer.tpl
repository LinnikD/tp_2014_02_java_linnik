<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Timer</title>
        <script src="/timer.js"></script>
    </head>
    <body onload='setInterval(function(){refresh()}, ${refreshPeriod} ); setClientTime();'>
        <p>Client time: <span id='ClientTime'></span></p>
        <p>Server time: ${time}</p>
        <p>Your id on the server: ${userId}</p>
        You can go to <a href="/index" > main </a> or <a href="/quit"> Exit!</a>
        <div>
            <img src="/kot4.jpeg"/>
        </div>
    </body>
</html>