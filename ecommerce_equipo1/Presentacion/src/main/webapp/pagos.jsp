<%-- 
    Document   : historialPagos
    Created on : 23 nov 2025, 12:54:01 p.m.
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Historial de pagos</title>
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/historialPagos.css">
    </head>
    <body>

        <main>
            <%@include file="jspf/sideMenu.jspf" %>

            <div class="contenido-admin">

                <h2 class="titulo-seccion">Historial de pagos</h2>

                <c:forEach var="p" items="${pedidos}">
                    <div class="tarjeta-pago">

                        <div class="col-pago">
                            <label class="label">Total</label>
                            <label class="value">$ ${p.pago.total}</label>
                        </div>

                        <div class="col-pago">
                            <label class="label">Usuario</label>
                            <label class="value">${p.direccion.nombreUsuario}</label>
                        </div>

                        <div class="col-pago">
                            <label class="label">Fecha</label>
                            <label class="value">${p.pago.fecha}</label>
                        </div>

                        <div class="col-pago">
                            <label class="label">Número de pedido</label>
                            <label class="value">#${p.numeroPedido}</label>
                        </div>

                        <div class="col-pago">
                            <label class="label">Método de pago</label>
                            <label class="value">Tarjeta</label>
                        </div>

                    </div>
                </c:forEach>

            </div>
        </main>
    </body>
</html>
