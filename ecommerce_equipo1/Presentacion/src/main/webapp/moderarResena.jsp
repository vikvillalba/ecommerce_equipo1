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
        <%@include file="jspf/header_admin.jspf" %>
        <%@include file="jspf/menu_mobile.jspf" %>
        <main>
            <%@include file="jspf/sideMenu.jspf" %>
            <div class="resenas">
                <h2>Panel de Moderación</h2>

                <%-- Verificamos si hay reseñas en la lista que mandó el Servlet --%>
                <c:if test="${not empty resenas}">
                    <%@ include file="jspf/resena_vistaAdmin.jspf" %>
                </c:if>

                <c:if test="${empty resenas}">
                    <p style="padding: 20px; text-align: center;">No hay reseñas registradas en el sistema.</p>
                </c:if>
            </div>
        </main>
                <%@include file="jspf/footer.jspf" %>
    </body>
</html>
