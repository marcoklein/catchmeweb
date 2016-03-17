<%-- 
    Document   : main
    Created on : Mar 16, 2016, 4:59:17 PM
    Author     : kleimarc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/assets/icons/font/flaticon.css">
<style>
    #world-container {
/*        width: 400px;
        height: 400px;*/
        background-color: white;
        border: black solid 3px;
    }
    #world {
    }
    #game {
        height: 100%;
        color: black;
    }
    #world-container {
        overflow: hidden;
        background-color: #99F;
    }
    #world td:not(.inactive) {
        width: 60px;
        height: 60px;
        background-color: #DDD;
        border: black solid 1px;
    }
    #world td.inactive {
        width: 60px;
        height: 60px;
        border: 0;
    }
    .flaticon-building {
    }
    
</style>
<div id="game" class="container">
        <h1>Catch Me</h1>
        <div id="world-container">
            <div id="world" class="center">
                <table>
                    <tr>
                        <td><span class="flaticon-building center"></span></td>
                        <td><i class="flaticon-home"></i></td>
                        <td></td>
                        <td class="inactive"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="inactive"></td>
                        <td></td>
                        <td><span class="fa fa-beer fa-2x"></span></td>
                        <td></td>
                        <td><span class="fa fa-bank fa-2x"></span></td>
                    </tr>
                    <tr>
                        <td class="inactive"></td>
                        <td><span class="fa fa-bomb fa-2x"></span></td>
                        <td><span class="fa fa-lightbulb-o fa-2x"></span></td>
                    </tr>
                    <tr>
                        <td><span class="fa fa-building fa-3x"></span></td>
                        <td><span class="fa fa-building fa-3x"></span></td>
                        <td></td>
                        <td><span class="fa fa-building fa-3x"></span></td>
                        <td><span class="fa fa-building fa-3x"></span></td>
                    </tr>
                        <td></td>
                        <td class="inactive"></td>
                        <td></td>
                        <td class="inactive"></td>
                    <tr>
                    </tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    <tr>
                    </tr>
                </table>
            </div>
        </div>
</div>

<script>
    /* Init of game */
    sendLogin();
    $("#world").draggable();
    
    $(window).resize(resize);
    resize();
    function resize() {
        // get size
        var width = $("#game").width();
//        console.log("width: " + width);
        
        var height = $("#game").height();
        // adjust size
        height -= $("#game>h1").height() + 128;
//        width -= 64;
        
        // apply size
//        $("#world").width(width);
//        $("#world").height(height);
        
        $("#world-container").width(width);
        $("#world-container").height(height);
    }
</script>
<script src="/js/app/catchme.js"></script>