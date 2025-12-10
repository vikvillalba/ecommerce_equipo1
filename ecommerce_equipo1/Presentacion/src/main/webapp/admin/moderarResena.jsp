<%-- 
    Document   : moderarResena
    Created on : 25 nov 2025, 8:53:57 p.m.
    Author     : victoria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Administrador - Moderar reseñas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/resenaAdmin.css">
    </head>
    <body>
        
        <%@include file="../jspf/header_admin.jspf" %>
        <%@include file="../jspf/menu_mobile.jspf" %>

        <main>
            
            <%@include file="../jspf/sideMenu.jspf" %>

            <div class="resenas">
                <h2>Moderar reseñas</h2>

                <c:if test="${not empty resenas}">
                    <%@ include file="../jspf/resena_vistaAdmin.jspf" %>
                </c:if>

                <c:if test="${empty resenas}">
                    <div class="review-card" style="text-align: center;">
                        <div class="info-group">
                            <p>No hay reseñas pendientes.</p>
                        </div>
                    </div>
                </c:if>
            </div>

        </main>

        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>