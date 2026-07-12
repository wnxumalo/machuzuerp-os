<%@page import="com.erpbyw.FileData"%>
<%@page import="com.erpbyw.Systems"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Download</title>
    </head>
    <body>
        <%
                FileData.downloadDocument(request, response, Systems.initConnection(), request.getParameter("uuid"), request.getParameter("recnum"));
        %>
    </body>
</html>