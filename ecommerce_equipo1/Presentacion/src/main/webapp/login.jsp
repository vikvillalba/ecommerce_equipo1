<%-- 
    Document   : login
    Created on : Nov 20, 2025, 8:17:26 PM
    Author     : Maryr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión - Sweet Blossom</title>
        <link rel="stylesheet" href="CSS/estiloLogin.css">
    </head>
    <body>
        <%
            // Determinar qué tab mostrar
            String mostrarRegistro = (String) request.getAttribute("mostrarRegistro");
            boolean esRegistro = mostrarRegistro != null && mostrarRegistro.equals("true");
            
            String error = (String) request.getAttribute("error");
        %>

        <div class="main-container">
            <div class="form-container">
                <div class="tabs">
                    <form action="/LoginServlet" method="GET" style="flex: 1; margin: 0;">
                        <button type="submit" class="tab <%= !esRegistro ? "active" : "" %>">
                            Iniciar sesión
                        </button>
                    </form>
                    <form action="RegistroServlet" method="GET" style="flex: 1; margin: 0;">
                        <button type="submit" class="tab <%= esRegistro ? "active" : "" %>">
                            Registrarse
                        </button>
                    </form>
                </div>

                <% if (error != null && !error.isEmpty()) { %>
                    <div style="padding: 15px; margin-bottom: 20px; background: #ffebee; border-radius: 10px; color: #c62828;">
                        <%= error %>
                    </div>
                <% } %>

                <% if (!esRegistro) { %>
                    <div class="form-content active">
                        <form action="LoginServlet" method="POST">
                            <div class="form-group">
                                <label for="correoLogin">Correo electrónico</label>
                                <input type="email" 
                                       id="correoLogin" 
                                       name="correo" 
                                       required>
                            </div>

                            <div class="form-group">
                                <label for="contrasenaLogin">Contraseña</label>
                                <input type="password" 
                                       id="contrasenaLogin" 
                                       name="contrasena" 
                                       required>
                            </div>

                            <button type="submit" class="btn-submit">
                                Iniciar sesión
                            </button>
                        </form>
                    </div>
                <% } %>

                <% if (esRegistro) { %>
                    <div class="form-content active">
                        <form action="/RegistroServlet" method="POST">
                            <div class="form-group">
                                <label for="nombre">Nombre completo *</label>
                                <input type="text" 
                                       id="nombre" 
                                       name="nombre" 
                                       required>
                            </div>

                            <div class="form-group">
                                <label for="telefono">Teléfono</label>
                                <input type="tel" 
                                       id="telefono" 
                                       name="telefono">
                            </div>

                            <div class="form-group">
                                <label for="correoRegistro">Correo Electrónico *</label>
                                <input type="email" 
                                       id="correoRegistro" 
                                       name="correo" 
                                       required>
                            </div>

                            <div class="form-group">
                                <label for="contrasenaRegistro">Contraseña *</label>
                                <input type="password" 
                                       id="contrasenaRegistro" 
                                       name="contrasena" 
                                       required>
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
                                        <option value="México" selected>México</option>
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
                                           name="codigoPostal">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="colonia">Colonia</label>
                                    <input type="text" 
                                           id="colonia" 
                                           name="colonia">
                                </div>

                                <div class="form-group">
                                    <label for="calle">Calle</label>
                                    <input type="text" 
                                           id="calle" 
                                           name="calle">
                                </div>

                                <div class="form-group" style="max-width: 120px;">
                                    <label for="numero">Número</label>
                                    <input type="text" 
                                           id="numero" 
                                           name="numero">
                                </div>
                            </div>

                            <button type="submit" class="btn-submit">
                                Registrarse
                            </button>
                        </form>
                    </div>
                <% } %>
            </div>
        </div>
    </body>
</html>