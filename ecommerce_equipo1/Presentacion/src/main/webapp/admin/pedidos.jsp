<%-- 
    Document   : pedidos
    Created on : 23 nov 2025, 11:05:22 a.m.
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Gestionar pedidos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/estiloPedido.css">
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
    <body style="margin:-8px">
        <%@include file="../jspf/header_admin.jspf" %>
        <%@include file="../jspf/menu_mobile.jspf" %>
        
        <!-- Contenedor del mensaje de feedback -->
        <div id="feedbackMessage" class="message-box" role="alert"></div>

        <main>
            <%@include file="../jspf/sideMenu.jspf" %>
            <div class="pedidos">

                <h2 class="titulo">Gestionar pedidos</h2>

                <c:forEach var="p" items="${pedidosLista}">

                    <form action="pedidos" method="POST" class="pedido-card">

                        <div class="col"> 
                            <label class="label">Número de pedido</label>
                            <label class="value">${p.numeroPedido}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Usuario</label>
                            <label class="value">${p.usuario}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Fecha</label>
                            <label class="value">${p.fecha}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Total</label>
                            <label class="value">$ ${p.total}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Estado</label>
                            <select name="estado">
                                <option value="PENDIENTE"
                                        <c:if test="${p.estado == 'PENDIENTE'}">selected</c:if>>
                                            Pendiente
                                        </option>

                                        <option value="ENVIADO"
                                        <c:if test="${p.estado == 'ENVIADO'}">selected</c:if>>
                                            Enviado
                                        </option>

                                        <option value="ENTREGADO"
                                        <c:if test="${p.estado == 'ENTREGADO'}">selected</c:if>>
                                            Entregado
                                        </option>
                            </select>
                        </div>

                        <input type="hidden" name="idPedido" value="${p.numeroPedido}">

                        <div class="col boton">
                            <button class="botonActualizar" type="submit">Actualizar estado</button>
                        </div>

                    </form>
                </c:forEach>

                <c:if test="${empty pedidosLista}">
                    <p class="sin-pedidos">No hay pedidos registrados.</p>
                </c:if>
            </div>
        </main>
        
        <!-- Script para mostrar mensajes de error/éxito -->
        <script>
            document.addEventListener('DOMContentLoaded', function() {
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
                    setTimeout(function() {
                        messageBox.classList.remove('show');
                        // Asegurar que el elemento se limpie y el tipo se quite después de la transición
                        setTimeout(function() {
                            messageBox.textContent = '';
                            messageBox.classList.remove(type);
                        }, 500); 
                    }, 5000);
                }
            });
        </script>
    </body>
</html>
<%@ include file="../jspf/footer.jspf" %>