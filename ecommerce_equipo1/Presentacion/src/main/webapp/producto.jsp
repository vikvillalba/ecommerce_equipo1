<%-- 
    Document   : producto
    Created on : 19 nov 2025, 8:10:04 p.m.
    Author     : erika
--%>
<%@page import="entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Producto p = (Producto) request.getAttribute("producto");

    if (p == null) {
%>
<h2>Producto no encontrado.</h2>
<a href="index.jsp">Volver a la tienda</a>
<%
        return;
    }
%>

<div class="producto-container">

    <img src="<%= p.getImagen()%>" class="main-img">

    <h1><%= p.getNombre()%></h1>

    <div class="precio">$<%= p.getPrecio()%></div>

    <p><%= p.getDescripcion()%></p>

    <h4>Especificaciones:</h4>
    <p><%= p.getEspecificaciones()%></p>

    <p>Existencias: <%= p.getExistencias()%></p>

    <% if (!p.isDisponibilidad()) { %>
    <p class="no-stock">Producto no disponible</p>
    <% }%>

</div>
