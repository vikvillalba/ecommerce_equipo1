<%-- Document : detallesPago Created on : 24 nov 2025, 22:36:25 Author : Alici --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalles de pago y envío | Sweet Blossom</title>
        <link rel="stylesheet" href="CSS/detallesPago.css">
        <style>
            .error-message-box {
                display: flex;
                align-items: center;
                gap: 15px;

                background-color: var(--color-baby-pink);
                border: 3px solid var(--color-pink);
                padding: 15px 20px;
                border-radius: 8px;
                margin-bottom: 25px;
                font-weight: 600;
                text-align: left;
                color: var(--color-brown);
            }

            .error-message-box strong {
                color: var(--color-pink);
            }

            .error-message-box svg {
                min-width: 24px;
                height: 24px;
                color: var(--color-pink);
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <%@include file="jspf/header_cliente.jspf" %>
        <main>
            <div class="container">
                <h1 class="main-title">Detalles de pago y envío</h1>
                <div class="progress-bar">
                    <div class="step step-inactive">
                        <div class="step-circle">1</div>
                        <div class="step-label">Carrito de compras</div>
                    </div>
                    <div class="step step-active">
                        <div class="step-circle">2</div>
                        <div class="step-label">Detalles de pago y envío</div>
                    </div>
                    <div class="step step-inactive">
                        <div class="step-circle">3</div>
                        <div class="step-label">Orden completa</div>
                    </div>
                </div>

               <c:if test="${not empty errorMensaje}">
                    <div class="error-message-box">
                        <!-- Icono SVG de Advertencia (Exclamación) -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-alert-triangle">
                            <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/>
                            <path d="M12 9v4"/>
                            <path d="M12 17h.01"/>
                        </svg>
                        <p><strong>Error de Validación:</strong> ${errorMensaje}</p>
                    </div>
                </c:if>

                <div class="checkout-container">

                    <div class="form-column">
                        <form action="detallesPedido" method="POST" id="checkout-form" enctype="multipart/form-data">
                            <!-- Nota: enctype es necesario para la subida de comprobante -->

                            <!--  Información de contacto -->
                            <section class="checkout-section">
                                <h2>Información de contacto</h2>
                                <div class="form-group">
                                    <label for="nombre-completo">NOMBRE COMPLETO</label>
                                    <input type="text" id="nombre-completo" name="nombre-completo" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="telefono">TELÉFONO</label>
                                    <input type="tel" id="telefono" name="telefono" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="correo-electronico">CORREO ELECTRÓNICO</label>
                                    <input type="email" id="correo-electronico" name="correo-electronico" placeholder="">
                                </div>
                            </section>

                            <!-- Dirección de envío -->
                            <section class="checkout-section">
                                <h2>Dirección de envío</h2>
                                <div class="grid-container grid-cols-2">
                                    <div class="form-group">
                                        <label for="estado">Estado</label>
                                        <input type="text" id="estado" name="estado" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="pais">País</label>
                                        <select id="pais" name="pais">
                                            <option value="">...</option>
                                            <option value="MEXICO" selected>México</option>
                                            <option value="USA">Estados Unidos</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="grid-container grid-cols-2">
                                    <div class="form-group">
                                        <label for="ciudad">Ciudad</label>
                                        <input type="text" id="ciudad" name="ciudad" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="codigo-postal">Código postal</label>
                                        <input type="text" id="codigo-postal" name="codigo-postal" placeholder="">
                                    </div>
                                </div>

                                <div class="grid-container grid-cols-3-ship">
                                    <div class="form-group">
                                        <label for="colonia">Colonia</label>
                                        <input type="text" id="colonia" name="colonia" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="calle">Calle</label>
                                        <input type="text" id="calle" name="calle" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="numero">Número</label>
                                        <input type="text" id="numero" name="numero" placeholder="">
                                    </div>
                                </div>
                            </section>

                            <!--  Método de pago -->
                            <section class="checkout-section">
                                <h2>Método de pago</h2>
                                <div class="payment-options-group">

                                    <!-- Opción: Transferencia -->
                                    <label class="payment-option" id="option-transferencia">
                                        <input type="radio" name="metodo-pago" value="TRANSFERENCIA"
                                               id="radio-transferencia">
                                        <label for="radio-transferencia">Transferencia</label>
                                        <span class="payment-option-dash"></span>
                                    </label>

                                    <!-- Detalles de Transferencia (Datos y subida de comprobante) -->
                                    <div class="payment-details-container" id="transferencia-details">

                                        <p style="font-size:0.95rem; color:var(--color-light-brown); margin-bottom:15px;">
                                            Por favor, realiza la transferencia bancaria con el monto total a
                                            los siguientes datos y sube el comprobante.
                                        </p>

                                        <div class="transfer-info-block">
                                            <p><strong>Beneficiario:</strong> Tienda Sweet Blossom S.A. de C.V.</p>
                                            <p><strong>Banco:</strong> Banco de Comercio (BCM)</p>
                                            <p><strong>Número de Cuenta:</strong> 5543 8976 1234 5678</p>
                                            <p><strong>CLABE Interbancaria:</strong> 012345678901234567</p>
                                            <p><strong>Monto Total:</strong> <span
                                                    style="font-size:1rem;">
                                                    $<fmt:formatNumber value="${totalCarrito}" pattern="0.00" />
                                                    MXN</span>
                                            </p>
                                        </div>

                                        <div class="form-group">
                                            <label for="comprobante-transferencia">SUBIR COMPROBANTE (PDF o JPG)</label>
                                            <input type="file" id="comprobante-transferencia" name="comprobante-transferencia"
                                                   accept=".pdf, .jpg, .jpeg, .png">
                                        </div>
                                    </div>

                                    <!-- Opción: Tarjeta (Seleccionada por defecto) -->
                                    <label class="payment-option selected" id="option-tarjeta">
                                        <input type="radio" name="metodo-pago" value="TARJETA"
                                               id="radio-tarjeta" checked>
                                        <label for="radio-tarjeta">Tarjeta</label>
                                        <span class="payment-option-dash"></span>
                                    </label>

                                    <!-- Detalles de Tarjeta -->
                                    <div class="payment-details-container active" id="card-details">
                                        <div class="form-group">
                                            <label for="propietario-tarjeta">NOMBRE DEL PROPIETARIO</label>
                                            <input type="text" id="propietario-tarjeta" name="propietario-tarjeta"
                                                   placeholder="Nombre como aparece en la tarjeta">
                                        </div>
                                        <div class="form-group">
                                            <label for="numero-tarjeta">NÚMERO DE TARJETA</label>
                                            <input type="text" id="numero-tarjeta" name="numero-tarjeta" value="">
                                        </div>
                                        <div class="grid-container grid-cols-2">
                                            <div class="form-group">
                                                <label for="fecha-caducidad">FECHA DE CADUCIDAD</label>
                                                <input type="text" id="fecha-caducidad" name="fecha-caducidad" placeholder="MM/AA">
                                            </div>
                                            <div class="form-group">
                                                <label for="cvc">CVV</label>
                                                <input type="text" id="cvc" name="cvc" placeholder="">
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Opción: Contra Entrega -->
                                    <label class="payment-option" id="option-contra-entrega">
                                        <input type="radio" name="metodo-pago" value="CONTRA_ENTREGA"
                                               id="radio-contra-entrega">
                                        <label for="radio-contra-entrega">Contra Entrega</label>
                                    </label>
                                </div>

                            </section>

                            <!-- Campo oculto para enviar el total de la compra -->
                            <input type="hidden" name="totalCompra" value="<fmt:formatNumber value="${totalCarrito}" pattern="#.00" />" />

                        </form>
                    </div>

                    <div class="summary-panel-wrapper">
                        <div class="summary-column">
                            <h2>Resumen de la compra</h2>
                            <c:set var="totalCarrito" value="0"/>
                            <c:forEach var="c" items="${productosCarrito}">
                                <!-- Item de Producto -->
                                <%@include file="jspf/card_producto_pagar.jspf" %>
                                <c:set var="totalCarrito" value="${totalCarrito + c.subtotal}" />
                            </c:forEach>

                            <!-- Total -->
                            <div class="summary-total">
                                <p>Total</p>
                                <p>
                                    $<fmt:formatNumber value="${totalCarrito}" pattern="0.00" />
                                </p>
                            </div>
                        </div>

                        <button class="place-order-button" onclick="document.getElementById('checkout-form').submit();">
                            Realizar pedido
                        </button>
                    </div>
                </div>


                <script>
                    document.addEventListener('DOMContentLoaded', () => {
                        const paymentRadios = document.querySelectorAll('input[name="metodo-pago"]');

                        // Elementos de detalles de pago
                        const cardDetails = document.getElementById('card-details');
                        const transferenciaDetails = document.getElementById('transferencia-details');

                        const paymentOptions = document.querySelectorAll('.payment-option');

                        // Array de todos los contenedores de detalles
                        const allDetailsContainers = [cardDetails, transferenciaDetails];

                        // Función para actualizar el estado visual y de contenido
                        function updatePaymentMethod() {
                            const checkedRadio = document.querySelector('input[name="metodo-pago"]:checked');
                            if (!checkedRadio)
                                return;

                            let selectedValue = checkedRadio.value;

                            paymentOptions.forEach(option => {
                                const radio = option.querySelector('input[type="radio"]');
                                if (radio && radio.value === selectedValue) {
                                    option.classList.add('selected');
                                } else {
                                    option.classList.remove('selected');
                                }
                            });

                            // Ocultar todos los detalles primero
                            allDetailsContainers.forEach(container => {
                                container.classList.remove('active');
                            });

                            // Mostrar el contenedor activo
                            if (selectedValue === 'TARJETA') {
                                cardDetails.classList.add('active');
                            } else if (selectedValue === 'TRANSFERENCIA') {
                                transferenciaDetails.classList.add('active');
                            }
                        }

                        // Añadir el listener a todos los botones de radio
                        paymentRadios.forEach(radio => {
                            radio.addEventListener('change', updatePaymentMethod);
                        });

                        updatePaymentMethod();
                    });
                </script>
            </div>
        </main>

        <!-- Footer -->
        <%@include file="jspf/footer.jspf" %>

    </body>

</html>