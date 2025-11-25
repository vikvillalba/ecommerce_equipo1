<%-- Document : carrito Created on : 24 nov 2025, 22:36:36 Author : Alici --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Carrito de Compras | Sweet Blossom</title>
            <link rel="stylesheet" href="CSS/carrito.css">
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
                        <!-- 2. Detalle del Carrito y Resumen -->
                        <form action="">
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
                                    <%@include file="jspf/card_producto_carrito.jspf" %>


                                </div>
                                <div class="cart-summary">
                                    <span class="summary-title">Resumen del carrito</span>
                                    <div class="summary-total summary-row">
                                        <span>Total</span>
                                        <span id="total-display">$39.00</span>
                                    </div>
                                    <button class="pay-button">
                                        Pagar
                                    </button>
                                </div>
                            </div>
                        </form>

                    </div>
                </main>

                <!-- Footer -->
                <%@include file="jspf/footer.jspf" %>

        </body>

        </html>