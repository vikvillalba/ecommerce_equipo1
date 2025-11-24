<%-- 
    Document   : historialPedidos
    Created on : 23 nov 2025, 4:43:38 p.m.
    Author     : erika
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Historial Pedidos</title>
        <link rel="stylesheet" href="CSS/estiloHistorial.css">
    </head>
    <body>

  

        <div class="main-container">

            <!-- Panel lateral -->
            <aside class="sidebar">
                <div class="user-box">
                    <p class="user-name">Sofía Havertz</p>
                </div>

                <ul class="menu-side">
                    <li><a href="#">Modificar datos</a></li>
                    <li class="active"><a href="#">Pedidos</a></li>
                    <li><a href="#">Cerrar sesión</a></li>
                </ul>
            </aside>

            <!-- Contenido principal -->
            <section class="content">
                <h1>Historial de Pedidos</h1>

                <table class="tabla-pedidos">
                    <thead>
                        <tr>
                            <th>Número de pedido</th>
                            <th>Fecha compra</th>
                            <th>Estado</th>
                            <th>Total pagado</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="p" items="${pedidos}">
                            <tr>
                                <td>#${p.id}</td>
                                <td>${p.fechaCompra}</td>
                                <td>${p.estado}</td>
                                <td>$${p.total}</td>
                            </tr>
                        </c:forEach>

                        <!-- Ejemplo estático por si la BD está vacía -->
                        <c:if test="${empty pedidos}">
                            <tr>
                                <td colspan="4" style="text-align: center;">No hay pedidos registrados</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </section>
        </div>

    </body>
</html>
