<%-- 
    Document   : escribirResena
    Created on : 22 nov 2025, 11:55:46 p.m.
    Author     : erika
--%>

<%@page import="entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    if (producto == null) {
%>
<h2>Producto no encontrado</h2>
<a href="CatalogoServlet">Volver al catálogo</a>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Escribir reseña</title>
        <link rel="stylesheet" href="CSS/estiloEscribirResena.css">
    </head>

    <body>

        <div class="modal-container">

            <div class="modal">

                <div class="modal-header">
                    <h2>Reseñar: <%= producto.getNombre()%></h2>

                    <a class="cerrar" href="ResenasServlet?id=<%= producto.getId()%>">✖</a>
                </div>

                <form action="GuardarResenaServlet" method="post">

                    <input type="hidden" name="idProducto" value="<%= producto.getId()%>">

                    <label>Escribe aquí tu opinión:</label>
                    <textarea name="comentario"></textarea>

                    <div class="calificacion-box">
                        <span>Calificación:</span>

                        <div class="estrellas">
                            <input type="radio" name="calificacion" value="1"> ⭐
                            <input type="radio" name="calificacion" value="2"> ⭐⭐
                            <input type="radio" name="calificacion" value="3"> ⭐⭐⭐
                            <input type="radio" name="calificacion" value="4"> ⭐⭐⭐⭐
                            <input type="radio" name="calificacion" value="5"> ⭐⭐⭐⭐⭐
                        </div>
                    </div>

                    <button class="btn-publicar" type="submit">Publicar reseña</button>

                </form>

            </div>

        </div>

    </body>
</html>

