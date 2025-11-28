<%--
    Document   : adminCuentas
    Created on : 23 nov 2025
    Author     : Jack Murrieta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Gestionar Cuentas</title>
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/gestionarProductos.css">
        <link rel="stylesheet" href="CSS/estiloAdminCuentas.css">
    </head>
    <body>
        <%@include file="jspf/header_admin.jspf" %>
        <%@include file="jspf/menu_celular.jspf" %>

        <main>
            <%@include file="jspf/sideMenu.jspf" %>
            <div class="contenedor__cuentas">
                <h2 class="titulo">Gestionar Cuentas</h2>

                <div class="lista__usuarios">
                    <c:forEach var="usuario" items="${usuarios}">
                        <%@include file="jspf/card_user.jspf" %>
                    </c:forEach>

                    <c:if test="${empty usuarios}">
                        <p class="sin-usuarios">No hay usuarios registrados.</p>
                    </c:if>
                </div>
            </div>
        </main>

        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
