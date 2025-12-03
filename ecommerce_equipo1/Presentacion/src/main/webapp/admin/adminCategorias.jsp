<%--
    Document   : adminCategorias
    Created on : 23 nov 2025
    Author     : Jack Murrieta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Gestionar Categorías</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/gestionarProductos.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/estiloAdminCategorias.css">
    </head>
    <body style="margin: -8px">
        <%@include file="../jspf/header_admin.jspf" %>
        <%@include file="../jspf/menu_mobile.jspf" %>

        <main>
            <%@include file="../jspf/sideMenu.jspf" %>
            <div class="contenedor__categorias">
                <h2 class="titulo">Gestionar Categorías</h2>

                <!-- Formulario para agregar nueva categoría -->
                <div class="form__nueva-categoria">
                    <form action="${pageContext.request.contextPath}/admin/AdminCategoriaServlet" method="post" class="form-agregar">
                        <input type="hidden" name="accion" value="agregar">
                        <input type="text" name="nombre" placeholder="Nombre de la categoría" required class="input__categoria">
                        <button type="submit" class="btn btn__agregar">Agregar</button>
                    </form>
                </div>

                <div class="lista__categorias">
                    <c:forEach var="categoria" items="${categorias}">
                        <%@include file="../jspf/card_categoria.jspf" %>
                    </c:forEach>

                    <c:if test="${empty categorias}">
                        <p class="sin-categorias">No hay categorías registradas.</p>
                    </c:if>
                </div>
            </div>
        </main>

        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>
