<%-- 
    Document   : producto
    Created on : 19 nov 2025, 8:10:04 p.m.
    Author     : erika
--%>
<%@page import="entidades.Producto"%>
<%@page import="enums.Tallas"%>
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
            <img src="${pageContext.request.contextPath}/<%= p.getImagen()%>" 
                 alt="<%= p.getNombre()%>" 
                 class="product-main-img"
                 onerror="this.src='${pageContext.request.contextPath}/img/placeholder.jpg'">
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
                <a href="<%= request.getContextPath()%>/ResenasServlet?id=<%= p.getId()%>" class="btn-reseñas">Ver reseñas</a>
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
                    <div class="color-dot" title="Color principal" style="background-color: <%= p.getColorHex() != null ? p.getColorHex() : "#ccc"%>;" aria-hidden="true"></div>
                </div>
            </div>

            <hr class="separator">

            <form id="addToCartForm" action="<%= request.getContextPath()%>/carrito" method="POST">

                <!-- Campos ocultos para enviar los datos del producto al Servlet -->
                <input type="hidden" name="accion" value="agregar">
                <input type="hidden" name="idProducto" value="<%= p.getId()%>">
                <input type="hidden" name="nombreProducto" value="<%= p.getNombre()%>">
                <input type="hidden" name="precio" value="<%= p.getPrecio()%>">
                <input type="hidden" name="direccionImagen" value="<%= p.getImagen()%>">
                <input type="hidden" name="colorHex" value="<%= p.getColorHex()%>">
                <input type="hidden" name="talla" id="tallaInput" value=""> <!-- Se llena con JS -->

                <div class="product-section">
                    <label class="size-label">Seleccionar Talla</label>
                    <div class="size-options" id="size-options-container">
                        <!-- Iteración sobre tallas si estuvieran disponibles dinámicamente -->
                        <% for (Tallas talla : Tallas.values()) {%>
                        <button type="button" class="size-btn" data-talla="<%= talla.name()%>">
                            <%= talla.name().replace("_", " ")%>
                        </button>
                        <% }%>
                    </div>
                    <p id="tallaError" style="color: red; margin-top: 10px; display: none;">Por favor, selecciona una talla.</p>
                </div>

                <div class="product-action-row">
                    <div class="qty-wrap">
                        <button type="button" class="qty-btn" id="qty-decrease" aria-label="Disminuir">−</button>
                        <input type="text" id="qty-input" name="cantidad" value="1" min="1" aria-label="Cantidad">
                        <button type="button" class="qty-btn" id="qty-increase" aria-label="Aumentar">+</button>
                        <span class="stock-info">(+<%= p.getExistencias()%> Disponibles)</span>
                    </div>

                    <div class="addcart-wrap">
                        <button type="submit" class="btn-addcart" id="addToCartButton">Añadir al carrito</button>
                    </div>
                </div>
            </form>
            <!-- FIN FORMULARIO DE CARRITO -->

        </div>
    </div>
</main>


<script>
    (function () {
        const inc = document.getElementById('qty-increase');
        const dec = document.getElementById('qty-decrease');
        const input = document.getElementById('qty-input');
        const tallaInput = document.getElementById('tallaInput');
        const tallaError = document.getElementById('tallaError');
        const form = document.getElementById('addToCartForm');
        const maxStock = <%= p.getExistencias()%>;

        // Control de Cantidad (asegurando min=1 y max=existencias)
        function updateQuantity(delta) {
            let current = parseInt(input.value || "1");
            let newQty = current + delta;

            newQty = Math.max(1, newQty); // Mínimo 1
            newQty = Math.min(maxStock, newQty); // Máximo existencias

            input.value = newQty;
        }

        inc && inc.addEventListener('click', () => updateQuantity(1));
        dec && dec.addEventListener('click', () => updateQuantity(-1));

        input.addEventListener('change', () => {
            updateQuantity(0); // Llama a la función de actualización sin cambio para forzar límites
        });


        // Selección de Talla
        document.querySelectorAll('.size-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.size-btn').forEach(b => b.classList.remove('active'));
                e.currentTarget.classList.add('active');

                // Actualiza el campo oculto con la talla seleccionada 
                tallaInput.value = e.currentTarget.getAttribute('data-talla');
                tallaError.style.display = 'none'; // Oculta el error si se selecciona
            });
        });

        //  Validación de Formulario (Talla seleccionada)
        form.addEventListener('submit', (e) => {
            if (tallaInput.value === "") {
                e.preventDefault(); // Detener el envío del formulario
                tallaError.style.display = 'block'; // Mostrar mensaje de error
                document.getElementById('size-options-container').scrollIntoView({behavior: 'smooth'});
            } else {
                tallaError.style.display = 'none';
            }
        });
    })();
</script>

</body>
</html>
<%@ include file="jspf/footer.jspf" %>