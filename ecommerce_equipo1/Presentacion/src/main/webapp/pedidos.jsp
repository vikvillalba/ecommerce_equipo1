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
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/estiloPedido.css">
    </head>
    <body style="margin:-8px">
        <%@include file="jspf/header_admin.jspf" %>
        <%@include file="jspf/menu_mobile.jspf" %>
        <main>
            <%@include file="jspf/sideMenu.jspf" %>
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
                            <button class="botonActualizar" type="input">Actualizar estado</button>
                        </div>

                    </form>
                </c:forEach>

                <c:if test="${empty pedidosLista}">
                    <p class="sin-pedidos">No hay pedidos registrados.</p>
                </c:if>
            </div>

        </main>

    </body>
</html>
<%@ include file="jspf/footer.jspf" %>
