<%-- 
    Document   : home
    Created on : Mar 16, 2016, 4:38:42 PM
    Author     : kleimarc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container center center-content-2">
    <h1>Home</h1>
    <h3>Wanna play a game?</h3>
    <button class="btn btn-primary btn-lg" onclick="play()">Play</button>
</div>
<script>
    function play() {
        ContentManager.showAjaxPage("/game");
    }
</script>
