<%-- Document : ordenCompleta Created on : 24 nov 2025, 22:36:36 Author : Alici --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Orden Completa | Sweet Blossom</title>
            <link rel="stylesheet" href="CSS/ordenCompleta.css">
        </head>

        <body>
            <!-- Header -->
            <%@include file="jspf/header_cliente.jspf" %>

                <main>
                    <div class="container">
                        <h1 class="main-title">Orden completa</h1>
                        <div class="progress-bar">
                            <div class="step step-inactive">
                                <div class="step-circle">1</div>
                                <div class="step-label">Carrito de compras</div>
                            </div>
                            <div class="step step-inactive">
                                <div class="step-circle">2</div>
                                <div class="step-label">Detalles de pago y envío</div>
                            </div>
                            <div class="step step-active">
                                <div class="step-circle">3</div>
                                <div class="step-label">Orden completa</div>
                            </div>
                        </div>
                        <div class="order-confirmation-card">
                            <h1 class="confirmation-title">Tu orden ha sido recibida</h1>

                            <div class="order-details-grid">
                                <div class="detail-label">Número de pedido:</div>
                                <div class="detail-value">#0123_45678</div>

                                <div class="detail-label">Fecha:</div>
                                <div class="detail-value">October 19, 2023</div>

                                <div class="detail-label total-label">Total:</div>
                                <div class="detail-value total-value">$1,345.00</div>

                                <div class="detail-label">Método de pago:</div>
                                <div class="detail-value">Credit Card</div>
                            </div>

                            <button class="history-button" onclick="window.location.href = '${pageContext.request.contextPath}/historialPedidos.jsp'">
                                Historial de pedidos
                            </button>
                        </div>
                    </div>
                </main>

                <!-- Footer -->
                <%@include file="jspf/footer.jspf" %>
        </body>

        </html>