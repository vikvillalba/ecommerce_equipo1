<%-- 
    Document   : adminProductos
    Created on : 9 dic 2025, 7:26:38 p.m.
    Author     : victoria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> <title>Administrador - Administrar productos</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/sideMenuAdmin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/gestionarProductos.css">

    </head>

    <body>
        <%@include file="../jspf/header_admin.jspf" %>

        <main class="main-container">
            <%@include file="../jspf/sideMenu.jspf" %>

            <%@include file="../jspf/menu_mobile.jspf" %>

            <div class="content-area">

                <div class="top-bar">
                    <button class="btn-agregar" onclick="abrirModal()">Agregar nuevo producto</button>
                </div>

                <div class="product-list">
                    <c:if test="${not empty productos}">
                        <c:forEach var="p" items="${productos}">
                            <%@include file="../jspf/card_producto_admin.jspf" %>
                        </c:forEach>
                    </c:if>

                    <c:if test="${empty productos}">
                        <p style="padding: 20px; color: #5D4037;">No hay productos registrados aún.</p>
                    </c:if>
                </div>
            </div>
        </main>

        <div id="modalCrear" class="modal-overlay">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="modalTitle">Agregar producto</h2>
                    <span class="close-btn" onclick="cerrarModal()">&times;</span>
                </div>

                <form action="${pageContext.request.contextPath}/admin/adminProductos" method="POST" enctype="multipart/form-data" class="form-producto">
                    <input type="hidden" name="accion" id="inpAccion" value="agregar">
                    <input type="hidden" name="id" id="inpId" value="">

                    <div class="form-grid">
                        <div class="col-img">
                            <div class="img-preview">
                                <i class="fa-solid fa-image" style="font-size: 40px; color: #ccc;"></i>
                            </div>
                            <label class="custom-file-upload">
                                <input type="file" name="imagen"> Seleccionar archivo
                            </label>
                        </div>

                        <div class="col-data">
                            <div class="form-group">
                                <label>Descripción</label>
                                <textarea name="descripcion" id="desc" rows="4"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Categorías</label>
                                <div class="cat-wrapper">
                                    <select name="idCategoria" id="cat" required>
                                        <option value="">Selecciona...</option>

                                        <c:forEach var="c" items="${listaCategorias}">
                                            <option value="${c.ordinal() + 1}">${c}</option>
                                        </c:forEach>

                                    </select>
                                    <button type="button" class="btn-plus">+</button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Especificaciones</label>
                                <textarea name="especificaciones" id="specs" rows="3"></textarea>
                            </div>
                        </div>
                    </div>

                    <div class="bottom-inputs">
                        <div class="form-group">
                            <label>Nombre</label>
                            <input type="text" name="nombre" id="nom" required>
                        </div>
                        <div class="form-group small">
                            <label>Precio</label>
                            <input type="number" step="0.01" name="precio" id="pre" required>
                        </div>
                        <div class="form-group small">
                            <label>Stock</label>
                            <input type="number" name="stock" id="stk" required>
                        </div>
                        <div class="form-group small">
                            <label>Color</label>
                            <input type="text" name="color" id="col">
                        </div>
                        <div class="form-group small">
                            <label>Talla</label>
                            <select name="talla" id="talla" required>
                                <option value="">--</option>

                                <c:forEach var="t" items="${listaTallas}">
                                    <option value="${t}">${t}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group btn-area">
                            <button type="submit" class="btn-guardar" id="btnSubmit">Agregar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script>

            const modal = document.getElementById("modalCrear");
            const form = document.querySelector(".form-producto");
            const modalTitle = document.getElementById("modalTitle");
            const btnSubmit = document.getElementById("btnSubmit");


            const inpAccion = document.getElementById("inpAccion");
            const inpId = document.getElementById("inpId");
            const inpNom = document.getElementById("nom");
            const inpDesc = document.getElementById("desc");
            const inpPre = document.getElementById("pre");
            const inpStk = document.getElementById("stk");
            const inpCol = document.getElementById("col");
            const inpSpecs = document.getElementById("specs");

            function abrirModal() {

                form.reset();
                inpAccion.value = "agregar";
                inpId.value = "";
                modalTitle.innerText = "Agregar producto";
                btnSubmit.innerText = "Agregar";
                modal.style.display = "flex";
            }

            function editarProducto(id, nombre, desc, precio, stock, color, specs, idCat, talla) {

                inpAccion.value = "editar";
                inpId.value = id;

                inpNom.value = nombre;
                inpDesc.value = desc;
                inpPre.value = precio;
                inpStk.value = stock;
                inpCol.value = color;
                inpSpecs.value = specs;

                document.getElementById("cat").value = idCat;
                document.getElementById("talla").value = talla;

                modalTitle.innerText = "Editar producto";
                btnSubmit.innerText = "Guardar Cambios";
                modal.style.display = "flex";
            }

            function cerrarModal() {
                modal.style.display = "none";
            }

            window.onclick = function (event) {
                if (event.target === modal) {
                    cerrarModal();
                }
            };
        </script>

        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>