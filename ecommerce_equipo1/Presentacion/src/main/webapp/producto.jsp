<%-- 
    Document   : producto
    Created on : 19 nov 2025, 8:10:04 p.m.
    Author     : erika
--%>
<%@page import="entidades.Producto"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="jspf/header_cliente.jspf" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ficha de producto | Sweet Blossom</title>

        <link rel="stylesheet" href="CSS/estiloProducto.css">
    </head>
    <body>

        <%
            Producto p = (Producto) request.getAttribute("producto");
            if (p == null) {
        %>
        <div class="producto-no-encontrado">
            <h2>Producto no encontrado.</h2>
            <a href="CatalogoServlet" class="btn-volver">Volver al catálogo</a>
        </div>
    </body>
</html>
<%
        return;
    }
%>

<div class="back-row">
    <a href="CatalogoServlet" class="back-link" title="Volver">
        <svg width="36" height="36" viewBox="0 0 24 24" fill="none">
        <path d="M15 18l-6-6 6-6" stroke="#7a4b3b" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
    </a>
</div>

<!-- MAIN -->
<main class="main-wrapper">
    <div class="product-grid">

        <div class="product-image-col">
            <img src="<%= p.getImagen()%>" 
                 alt="<%= p.getNombre()%>" 
                 class="product-main-img"
                 onerror="this.src='img/placeholder.jpg'">
        </div>


        <div class="product-details-col">
            <h1 class="product-title"><%= p.getNombre()%></h1>

            <div class="rating-row">
                <div class="stars">
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star">★</span>
                    <span class="star half">★</span>
                </div>
                <div class="rating-value">4.5/5</div>
                <a href="ResenasServlet?id=<%= p.getId()%>" class="btn-reseñas">Ver reseñas</a>
            </div>

            <div class="price-row">
                <div class="price-amount">$<%= String.format("%.2f", p.getPrecio())%></div>
            </div>

            <div class="product-section">
                <h4>Descripción:</h4>
                <p class="product-desc"><%= p.getDescripcion()%></p>
            </div>

            <div class="product-section two-col">
                <div>
                    <h4>Especificaciones técnicas:</h4>
                    <p class="product-specs"><%= p.getEspecificaciones()%></p>
                </div>
                <div class="product-color">
                    <label>Color:</label>
                    <div class="color-dot" title="Color principal" aria-hidden="true"></div>
                </div>
            </div>

            <hr class="separator">

            <div class="product-section">
                <label class="size-label">Seleccionar Talla</label>
                <div class="size-options">
                    <button class="size-btn">Extra chica</button>
                    <button class="size-btn">Chico</button>
                    <button class="size-btn">Mediano</button>
                    <button class="size-btn">Grande</button>
                    <button class="size-btn">Extra grande</button>
                </div>
            </div>

            <div class="product-action-row">
                <div class="qty-wrap">
                    <button class="qty-btn" id="qty-decrease" aria-label="Disminuir">−</button>
                    <input type="text" id="qty-input" value="1" aria-label="Cantidad">
                    <button class="qty-btn" id="qty-increase" aria-label="Aumentar">+</button>
                    <span class="stock-info">(+<%= p.getExistencias()%> Disponibles)</span>
                </div>

                <div class="addcart-wrap">
                    <button class="btn-addcart">Añadir al carrito</button>
                </div>
            </div>

        </div>
    </div>
</main>


<script>
    (function () {
        const inc = document.getElementById('qty-increase');
        const dec = document.getElementById('qty-decrease');
        const input = document.getElementById('qty-input');

        inc && inc.addEventListener('click', function () {
            input.value = Math.max(1, parseInt(input.value || "1") + 1);
        });
        dec && dec.addEventListener('click', function () {
            input.value = Math.max(1, parseInt(input.value || "1") - 1);
        });

        document.querySelectorAll('.size-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.size-btn').forEach(b => b.classList.remove('active'));
                e.currentTarget.classList.add('active');
            });
        });
    })();
</script>

</body>
</html>
<%@ include file="jspf/footer.jspf" %>
