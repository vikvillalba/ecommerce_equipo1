<%-- 
    Document   : login
    Created on : Nov 20, 2025, 8:17:26 PM
    Author     : Maryr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión - Sweet Blossom</title>
        <link rel="stylesheet" href="CSS/estiloLogin.css">
    </head>
    <body>

        <div class="main-container">
            <div class="form-container">
                <div class="tabs">
                    <button class="tab ${empty mostrarRegistro ? 'active' : ''}" 
                            onclick="mostrarTab('login')">
                        Iniciar sesión
                    </button>
                    <button class="tab ${not empty mostrarRegistro ? 'active' : ''}" 
                            onclick="mostrarTab('registro')">
                        Registrarse
                    </button>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-error">
                        <i class="fas fa-exclamation-circle"></i>
                        ${error}
                    </div>
                </c:if>

                <div id="loginForm" class="form-content ${empty mostrarRegistro ? 'active' : ''}">
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

                <!-- Formulario de Registro -->
                <div id="registroForm" class="form-content ${not empty mostrarRegistro ? 'active' : ''}">
                    <form action="RegistroServlet" method="POST">
                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input type="tel" 
                                   id="telefono" 
                                   name="telefono" 
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="correoRegistro">Correo Electrónico</label>
                            <input type="email" 
                                   id="correoRegistro" 
                                   name="correo" 
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="contrasenaRegistro">Contraseña</label>
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
            </div>
        </div>

        <script>
            function mostrarTab(tab) {
                // Ocultar todos los formularios
                document.querySelectorAll('.form-content').forEach(form => {
                    form.classList.remove('active');
                });

                // Remover clase active de todos los tabs
                document.querySelectorAll('.tab').forEach(button => {
                    button.classList.remove('active');
                });

                // Mostrar el formulario seleccionado
                if (tab === 'login') {
                    document.getElementById('loginForm').classList.add('active');
                    document.querySelectorAll('.tab')[0].classList.add('active');
                } else {
                    document.getElementById('registroForm').classList.add('active');
                    document.querySelectorAll('.tab')[1].classList.add('active');
                }
            }
        </script>
    </body>
</html>