<%-- 
    Document   : index
    Created on : 09.06.2017, 15:46:26
    Author     : maddin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="leaflet/leaflet.js" type="text/javascript"></script>
        <link href="leaflet/leaflet.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
        <script>
            document.addEventListener('DOMContentLoaded', initMap, false);
            function initMap() {
                var mymap = L.map('mapid').setView([54.083336, 12.108811], 13);
                L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a>',
                    maxZoom: 18,
                    id: 'your.mapbox.project.id',
                    accessToken: 'your.mapbox.public.access.token'
                }
                ).addTo(mymap);
            }
        </script>
    </head>
    <body>
        <h1>Hello Map!</h1>
        <div id="mapid" style="height: 480px; "></div>

    </body>
</html>
