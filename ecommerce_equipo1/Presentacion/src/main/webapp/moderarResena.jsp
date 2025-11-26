<%-- 
    Document   : moderarResena
    Created on : 25 nov 2025, 8:53:57 p.m.
    Author     : victoria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Moderar reseñas</title>
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/resenaAdmin.css">
    </head>
    <body>
        <main>
            <%@include file="jspf/sideMenu.jspf" %>
            <div class="resenas">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${not empty p.resenas}">
                        <c:set var="producto" value="${p}" />
                        <c:set var="resenas" value="${p.resenas}" />

                        <%@ include file="jspf/resena_vistaAdmin.jspf" %>
                        <hr>
                    </c:if>
                </c:forEach>
            </div>


        </main>
    </body>
</html>
