<%-- 
    Document   : resenas
    Created on : 22 nov 2025, 11:40:53 p.m.
    Author     : erika
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Resena"%>
<%@page import="entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="jspf/header_cliente.jspf" %>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    if (producto == null) {
%>
<h2>Producto no encontrado</h2>
<a href="CatalogoServlet">Volver al catálogo</a>
<%
        return;
    }

    List<Resena> resenas = producto.getResenas();
    if (resenas == null) {
        resenas = new ArrayList<>();
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reseñas - <%= producto.getNombre()%></title>
        <link rel="stylesheet" href="CSS/estiloResenas.css">
    </head>
    <body>
        <div class="titulo-section">
            <h1>Reseñas de: <%= producto.getNombre()%></h1>
            <a href="EscribirResenaServlet?id=<%= producto.getId() %>" class="btn-nueva">Escribir una reseña</a>

        </div>
        <section class="contenedor-resenas">
            <h3>Todas las reseñas</h3>

            <%
                for (Resena r : resenas) {
            %>
            <div class="card-resena">
                <div class="stars">
                    <% for (int i = 0; i < r.getCalificacion(); i++) { %>
                    ⭐
                    <% }%>
                </div>

                <h4><%= r.getCliente().getNombre()%></h4>
                <p class="comentario"><%= r.getComentario()%></p>

                <p class="fecha">Publicado recientemente</p>
            </div>
            <% }%>
        </section>
    </body>
</html>
<%@ include file="jspf/footer.jspf" %>