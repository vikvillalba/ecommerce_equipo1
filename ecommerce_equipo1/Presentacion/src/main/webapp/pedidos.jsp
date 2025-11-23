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
        <link rel="stylesheet" href="CSS/gestionarPedidos.css">
    </head>
    <body>
        
        <main>
            <%@include file="jspf/sideMenu.jspf" %>
            <div class="pedidos">

                <h2 class="titulo">Gestionar pedidos</h2>

                <c:forEach var="p" items="${pedidos}">

                    <form action="actualizarEstadoPedido" method="POST" class="pedido-card">

                        <div class="col"> 
                            <label class="label">Número de pedido</label>
                            <label class="value">#${p.numeroPedido}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Fecha</label>
                            <label class="value">${p.pago.fecha}</label>
                        </div>

                        <div class="col"> 
                            <label class="label">Total</label>
                            <label class="value">$ ${p.pago.total}</label>
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
                            <button type="submit">Actualizar estado</button>
                        </div>

                    </form>
                </c:forEach>
            </div>

        </main>

    </body>
</html>
