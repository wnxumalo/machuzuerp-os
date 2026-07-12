<%@page import="com.erpbyw.RequisitionData"%>
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
                RequisitionData.viewFile("MoA.docx", request, response);
        %>
    </body>
</html>