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
        <style>
            /* Contenedor del mensaje */
            .message-box {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 15px 25px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                color: white;
                font-weight: bold;
                opacity: 0;
                transition: opacity 0.5s, transform 0.5s;
                transform: translateY(-20px);
                z-index: 1000;
            }
            .message-box.show {
                opacity: 1;
                transform: translateY(0);
            }
            .message-box.error {
                background-color: #dc3545; /* Rojo */
            }
            .message-box.success {
                background-color: #28a745; /* Verde */
            }
        </style>
    </head>
    <body style="margin: -8px">
        <%@include file="../jspf/header_admin.jspf" %>
        <%@include file="../jspf/menu_mobile.jspf" %>

        <!-- Contenedor del mensaje de feedback -->
        <div id="feedbackMessage" class="message-box" role="alert"></div>

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

        <!-- Script para mostrar mensajes de error/éxito -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var messageBox = document.getElementById('feedbackMessage');

                // Leer el mensaje de error de la sesión (y borrarlo)
                var errorMessage = '<c:out value="${sessionScope.errorMessage}" />';
                var successMessage = '<c:out value="${sessionScope.successMessage}" />';

                var message = '';
                var type = '';

                if (errorMessage && errorMessage.trim() !== '') {
                    message = errorMessage;
                    type = 'error';
                    // Borrar el atributo de sesión después de mostrarlo
            <c:remove var="errorMessage" scope="session"/>
                } else if (successMessage && successMessage.trim() !== '') {
                    message = successMessage;
                    type = 'success';
                    // Borrar el atributo de sesión después de mostrarlo
            <c:remove var="successMessage" scope="session"/>
                }

                if (message) {
                    messageBox.textContent = message;
                    messageBox.classList.add(type);
                    messageBox.classList.add('show');

                    // Ocultar el mensaje después de 5 segundos
                    setTimeout(function () {
                        messageBox.classList.remove('show');
                        // Asegurar que el elemento se limpie y el tipo se quite después de la transición
                        setTimeout(function () {
                            messageBox.textContent = '';
                            messageBox.classList.remove(type);
                        }, 500);
                    }, 5000);
                }
            });
        </script>
    </body>
</html>