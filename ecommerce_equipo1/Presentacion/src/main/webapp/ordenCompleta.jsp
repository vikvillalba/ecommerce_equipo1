<%-- Document : ordenCompleta Created on : 24 nov 2025, 22:36:36 Author : Alici --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                    <h1 class="confirmation-title">¡Tu orden ha sido recibida!</h1>
                    <p style="margin-bottom: 2rem; color: var(--color-light-brown);">Recibirás un correo de confirmación con los detalles de tu compra y envío.</p>

                    <div class="order-details-grid">
                        <div class="detail-label">Número de pedido:</div>
                        <div class="detail-value"><c:out value="${numeroOrden}" /></div>

                        <div class="detail-label">Fecha:</div>
                        <div class="detail-value"><c:out value="${fecha}" /></div>

                        <div class="detail-label">Método de pago:</div>
                        <div class="detail-value"><c:out value="${metodoPago}" /></div>

                    </div>

                    <button class="history-button" onclick="window.location.href = '${pageContext.request.contextPath}/HistorialPedidost'">
                        Ver historial de pedidos
                    </button>
                </div>
            </div>
        </main>

        <!-- Footer -->
        <%@include file="jspf/footer.jspf" %>
    </body>

</html>