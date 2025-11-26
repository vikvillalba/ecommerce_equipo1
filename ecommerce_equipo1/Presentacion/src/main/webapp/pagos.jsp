<%-- 
    Document   : historialPagos
    Created on : 23 nov 2025, 12:54:01 p.m.
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Historial de pagos</title>
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/estiloHistorialPago.css">
    </head>
    <body>

        <main>
            <%@include file="jspf/sideMenu.jspf" %>

            <div class="contenido-admin">

                <h2 class="titulo-seccion">Historial de pagos</h2>

                <c:forEach var="p" items="${pagos}">
                    <div class="tarjeta-pago">

                        <div class="col-pago">
                            <label class="label">Total</label>
                            <label class="value">$ ${p.pago.total}</label>
                        </div>

                        <div class="col-pago">
                            <label class="label">Usuario</label>
                            <label class="value">Nombre de usuario</label>
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
                            <label class="value">${p.pago.metodoPago}</label>
                        </div>

                    </div>
                </c:forEach>
                <c:if test="${empty pagos}">
                    <p class="sin-pagos">No hay pagos registrados.</p>
                </c:if>
            </div>
        </main>
    </body>
</html>
<%@ include file="jspf/footer.jspf" %>
