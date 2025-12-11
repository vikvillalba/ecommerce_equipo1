<%-- Document : carrito 
     Created on : 24 nov 2025, 22:36:36 
     Author : Alici --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- Importación JSTL fmt -->

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Carrito de Compras | Sweet Blossom</title>
        <link rel="stylesheet" href="CSS/carrito.css">

        <style>
            .empty-cart-message {
                text-align: center;
                padding: 40px 20px;
                background-color: #fff0fb;
                border-radius: 8px;
                margin-top: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            }
            .empty-cart-message h2 {
                color: #333;
                margin-bottom: 10px;
            }
            .empty-cart-message p {
                color: #666;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <%@include file="jspf/header_cliente.jspf" %>

        <main>
            <div class="container">
                <h1 class="main-title">Carrito de compras</h1>
                <div class="progress-bar">
                    <div class="step step-active">
                        <div class="step-circle">1</div>
                        <div class="step-label">Carrito de compras</div>
                    </div>
                    <div class="step step-inactive">
                        <div class="step-circle">2</div>
                        <div class="step-label">Detalles de pago y envío</div>
                    </div>
                    <div class="step step-inactive">
                        <div class="step-circle">3</div>
                        <div class="step-label">Orden completa</div>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${empty productosCarrito}">
                        <div class="empty-cart-message">
                            <h2>¡Tu carrito está vacío!</h2>
                            <p>Parece que aún no has agregado productos. Explora nuestra tienda para comenzar a comprar.</p>
                            <a href="CatalogoServlet" class="pay-button" style="display: inline-block; margin-top: 15px;">Ir a la tienda</a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="cart-layout">
                            <!-- Columna Izquierda: Productos en el Carrito -->
                            <div class="cart-details">
                                <!-- Encabezados de la tabla (Ocultos en móvil) -->
                                <div class="cart-headers">
                                    <div class="product-col">Producto</div>
                                    <div class="quantity-col">Cantidad</div>
                                    <div class="price-col" style="width: 15%; text-align: right;">Precio</div>
                                    <div class="subtotal-col">Subtotal</div>
                                </div>

                                <!-- Fila de Producto -->
                                <c:set var="totalCarrito" value="0"/>
                                <c:forEach var="c" items="${productosCarrito}">
                                    <%-- Incluye la tarjeta del producto --%>
                                    <%@include file="jspf/card_producto_carrito.jspf" %>
                                    <c:set var="totalCarrito" value="${totalCarrito + c.subtotal}" />
                                </c:forEach>

                            </div>
                            <!-- Columna Derecha: Resumen del carrito -->
                            <div class="cart-summary">
                                <span class="summary-title">Resumen del carrito</span>

                                <div class="summary-total summary-row">
                                    <span>Total</span>
                                    <!-- Formato de dos decimales -->
                                    <span id="total-display">
                                        $<fmt:formatNumber value="${totalCarrito}" pattern="0.00" />
                                    </span>
                                </div>
                                <!-- Botón Pagar (Formulario separado) -->
                                <form action="detallesPedido" method="get">
                                    <button type="submit" class="pay-button">
                                        Pagar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </main>

        <!-- Footer -->
        <%@include file="jspf/footer.jspf" %>

    </body>

</html>