<%-- 
    Document   : Perfil
    Created on : Nov 25, 2025, 11:19:49 PM
    Author     : Maryr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="jspf/header_cliente.jspf" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="CSS/estiloModificarPerfil.css">
    </head>
    <body>
        <div class="main-container">
            <aside class="sidebar">
                <div class="user-box">
                    <p class="user-name">${sessionScope.usuarioNombre != null ? sessionScope.usuarioNombre : 'Usuario'}</p>
                </div>
                <ul class="menu-side">
                    <li class="active"><a href="#">Modificar datos</a></li>
                    <li><a href="HistorialPedidost">Pedidos</a></li>
                    <li><a href="${pageContext.request.contextPath}/LogOutServlet">Cerrar sesión</a></li>
                </ul>
            </aside>
            <section class="content">
                <div class="form-wrapper">
                    <h1>Datos</h1>

                    <form action="ModificarPerfilServlet" method="POST" class="form-datos">
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text"
                                   id="nombre"
                                   name="nombre"
                                   value="${sessionScope.usuario.nombre}"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input type="tel"
                                   id="telefono"
                                   name="telefono"
                                   value="${sessionScope.usuario.telefono}">
                        </div>

                        <div class="form-group">
                            <label for="contrasena">Contraseña</label>
                            <input type="password" 
                                   id="contrasena" 
                                   name="contrasena"
                                   placeholder="Dejar en blanco para no cambiar">
                        </div>

                        <div class="form-group">
                            <label for="confirmaContrasena">Confirma tu contraseña</label>
                            <input type="password" 
                                   id="confirmaContrasena" 
                                   name="confirmaContrasena">
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="estado">Estado</label>
                                <input type="text" 
                                       id="estado" 
                                       name="estado">
                            </div>

                            <div class="form-group">
                                <label for="pais">País</label>
                                <select id="pais" name="pais">
                                    <option value="">Selecciona...</option>
                                    <option value="México">México</option>
                                    <option value="Estados Unidos">Estados Unidos</option>
                                    <option value="Canadá">Canadá</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="ciudad">Ciudad</label>
                                <input type="text" 
                                       id="ciudad" 
                                       name="ciudad">
                            </div>

                            <div class="form-group">
                                <label for="codigoPostal">Código postal</label>
                                <input type="text"
                                       id="codigoPostal"
                                       name="codigoPostal"
                                       value="${sessionScope.usuario.direccion.codigoPostal}">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="colonia">Colonia</label>
                                <input type="text"
                                       id="colonia"
                                       name="colonia"
                                       value="${sessionScope.usuario.direccion.colonia}">
                            </div>

                            <div class="form-group">
                                <label for="calle">Calle</label>
                                <input type="text"
                                       id="calle"
                                       name="calle"
                                       value="${sessionScope.usuario.direccion.calle}">
                            </div>

                            <div class="form-group form-group-small">
                                <label for="numero">Número</label>
                                <input type="text"
                                       id="numero"
                                       name="numero"
                                       value="${sessionScope.usuario.direccion.numero}">
                            </div>
                        </div>

                        <button type="submit" class="btn-guardar">Guardar</button>
                    </form>
                </div>
            </section>
        </div>
    </body>
</html>
<%@ include file="jspf/footer.jspf" %>