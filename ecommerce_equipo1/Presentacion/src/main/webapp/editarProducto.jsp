<%-- 
    Document   : agregarProducto
    Created on : 22 nov 2025, 7:34:14 p.m.
    Author     : victoria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Nuevo producto</title>
        <link rel="stylesheet" href="CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="CSS/gestionarProductos.css">
    </head>
    <body>
        <main>
            <%@include file="jspf/sideMenu.jspf" %>
            <div class="producto">
                <h2 class="titulo">Agregar producto</h2>

                <form action="actualizarProducto" method="POST" enctype="multipart/form-data">
                    <div class="contenedor1">
                        <div class="sec1">
                            <img src="img/rr1.jpg" width="280" height="373">
                            <label for="imagen">Selecciona una imagen</label>
                            <input type="file" id="imagen" name="imagen" accept=".jpg,.jpeg,.png">
                        </div>  
                        <div class="sec1"> 

                            <label for="descripcion">Descripción</label>
                            <textarea id="descripcion" name="descripcion" rows="10" cols="50"></textarea>
                            <label for="especificaciones">Especificaciones:</label>
                            <textarea id="especificaciones" name="especificaciones" rows="10" cols="50"></textarea>
                        </div>
                        <div class="sec1"> 
                            <label for="categoria">Categoría</label>
                            <select id="categoria" name="categoria">
                                <c:forEach var="cat" items="${categorias}">
                                    <option value="${cat}">${cat}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="contenedor2">
                        <div class="sec1">
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre">     
                        </div>
                        <div class="contenedor3">
                            <div class="sec1">
                                <label for="precio">Precio</label>
                                <input type="number" name="precio">     
                            </div>
                            <div class="sec1">
                                <label for="existencias">Existencias</label>
                                <input type="number" name="existencias">     
                            </div>
                            <div class="sec1">
                                <label for="color">Color</label>
                                <input type="text" name="color">     
                            </div>
                            <div class="boton">
                                <input type="submit" name="Actualizar" class="botonEnviar">     
                            </div>
                        </div>
                    </div>




                </form>
            </div>
        </main>
    </body>
</html>
