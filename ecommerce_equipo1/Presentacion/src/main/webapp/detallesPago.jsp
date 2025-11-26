<%-- Document : detallesPago Created on : 24 nov 2025, 22:36:25 Author : Alici --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalles de pago y envío | Sweet Blossom</title>
        <link rel="stylesheet" href="CSS/detallesPago.css">
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
                <div class="checkout-container">

                    <!-- Columna Izquierda: Formularios de Pago y Contacto -->
                    <form action="ordenCompleta.jsp">
                        <div class="form-column">

                            <!-- 1. Información de contacto -->
                            <section class="checkout-section">
                                <h2>Información de contacto</h2>
                                <div class="form-group">
                                    <label for="nombre-completo">NOMBRE COMPLETO</label>
                                    <input type="text" id="nombre-completo" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="telefono">TELÉFONO</label>
                                    <input type="tel" id="telefono" placeholder="">
                                </div>
                                <div class="form-group">
                                    <label for="correo-electronico">CORREO ELECTRÓNICO</label>
                                    <input type="email" id="correo-electronico" placeholder="">
                                </div>
                                <div class="checkbox-group">
                                    <input type="checkbox" id="usar-datos-cuenta">
                                    <label for="usar-datos-cuenta">Usar datos asociados a mi cuenta</label>
                                </div>
                            </section>

                            <!-- 2. Dirección de envío -->
                            <section class="checkout-section">
                                <h2>Dirección de envío</h2>
                                <div class="grid-container grid-cols-2">
                                    <div class="form-group">
                                        <label for="estado">Estado</label>
                                        <input type="text" id="estado" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="pais">País</label>
                                        <select id="pais">
                                            <option value="">...</option>
                                            <option value="MX" selected>México</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="grid-container grid-cols-2">
                                    <div class="form-group">
                                        <label for="ciudad">Ciudad</label>
                                        <input type="text" id="ciudad" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="codigo-postal">Código postal</label>
                                        <input type="text" id="codigo-postal" placeholder="">
                                    </div>
                                </div>

                                <div class="grid-container grid-cols-3-ship">
                                    <div class="form-group">
                                        <label for="colonia">Colonia</label>
                                        <input type="text" id="colonia" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="calle">Calle</label>
                                        <input type="text" id="calle" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label for="numero">Número</label>
                                        <input type="text" id="numero" placeholder="">
                                    </div>
                                </div>

                                <div class="checkbox-group">
                                    <input type="checkbox" id="usar-direccion-cuenta">
                                    <label for="usar-direccion-cuenta">Usar dirección asociada a mi
                                        cuenta</label>
                                </div>
                            </section>

                            <!-- 3. Método de pago -->
                            <section class="checkout-section">
                                <h2>Método de pago</h2>
                                <div class="payment-options-group">

                                    <!-- Opción: Transferencia -->
                                    <label class="payment-option" id="option-transferencia">
                                        <input type="radio" name="metodo-pago" value="transferencia"
                                               id="radio-transferencia">
                                        <label for="radio-transferencia">Transferencia</label>
                                        <span class="payment-option-dash"></span>
                                    </label>

                                    <!-- Detalles de Transferencia (Datos y subida de comprobante) -->
                                    <div class="payment-details-container" id="transferencia-details">

                                        <p style="font-size:0.9rem; color:#82614e; margin-bottom:15px;">
                                            Por favor, realiza la transferencia bancaria con el monto total a
                                            los
                                            siguientes
                                            datos y
                                            sube el comprobante.
                                        </p>

                                        <div class="transfer-info-block">
                                            <p><strong>Beneficiario:</strong> Tienda Sweet Blossom S.A. de C.V.
                                            </p>
                                            <p><strong>Banco:</strong> Banco de Comercio (BCM)</p>
                                            <p><strong>Número de Cuenta:</strong> 5543 8976 1234 5678</p>
                                            <p><strong>CLABE Interbancaria:</strong> 012345678901234567</p>
                                            <p><strong>Monto Total:</strong> <span
                                                    style="font-size:1rem;">$39.00
                                                    MXN</span>
                                            </p>
                                        </div>

                                        <div class="form-group">
                                            <label for="comprobante-transferencia">SUBIR COMPROBANTE (PDF o
                                                JPG)</label>
                                            <input type="file" id="comprobante-transferencia"
                                                   accept=".pdf, .jpg, .jpeg, .png">
                                        </div>
                                    </div>

                                    <!-- Opción: Tarjeta (Seleccionada) -->
                                    <label class="payment-option selected" id="option-tarjeta">
                                        <input type="radio" name="metodo-pago" value="tarjeta"
                                               id="radio-tarjeta" checked>
                                        <label for="radio-tarjeta">Tarjeta</label>
                                        <span class="payment-option-dash"></span>
                                    </label>

                                    <!-- Detalles de Tarjeta -->
                                    <div class="payment-details-container" id="card-details">
                                        <div class="form-group">
                                            <label for="propietario-tarjeta">NOMBRE DEL PROPIETARIO</label>
                                            <input type="text" id="propietario-tarjeta"
                                                   placeholder="Nombre como aparece en la tarjeta">
                                        </div>
                                        <div class="form-group">
                                            <label for="numero-tarjeta">NÚMERO DE TARJETA</label>
                                            <input type="text" id="numero-tarjeta" value="">
                                        </div>
                                        <div class="grid-container grid-cols-2">
                                            <div class="form-group">
                                                <label for="fecha-caducidad">FECHA DE CADUCIDAD</label>
                                                <input type="text" id="fecha-caducidad" placeholder="MM/AA">
                                            </div>
                                            <div class="form-group">
                                                <label for="cvc">CVV</label>
                                                <input type="text" id="cvc" placeholder="">
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Opción: Contra Entrega -->
                                    <label class="payment-option" id="option-contra-entrega">
                                        <input type="radio" name="metodo-pago" value="contra-entrega"
                                               id="radio-contra-entrega">
                                        <label for="radio-contra-entrega">Contra Entrega</label>
                                    </label>
                                </div>

                            </section>
                    </form>
                    <!-- Botón Final -->
                    <button class="place-order-button" >
                        Realizar pedido
                    </button>

                </div>

                <!-- Columna Derecha: Resumen de la compra -->
                <div class="summary-column">
                    <h2>Resumen de la compra</h2>

                    <!-- Item de Producto -->
                    <%@include file="jspf/card_producto_pagar.jspf" %>

                    <!-- Total -->
                    <div class="summary-total">
                        <p>Total</p>
                        <p>$39.00</p>
                    </div>
                </div>
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
                    // Se asegura de que haya un radio seleccionado antes de continuar
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
                    if (selectedValue === 'tarjeta') {
                        cardDetails.classList.add('active');
                    } else if (selectedValue === 'transferencia') {
                        transferenciaDetails.classList.add('active');
                    }
                    // Para 'contra-entrega' no se muestra ningún formulario adicional
                }

                // Añadir el listener a todos los botones de radio
                paymentRadios.forEach(radio => {
                    radio.addEventListener('change', updatePaymentMethod);
                });

                // Ejecutar al cargar la página para reflejar la opción inicial (Tarjeta)
                updatePaymentMethod();
            });
        </script>
    </main>

    <!-- Footer -->
    <%@include file="jspf/footer.jspf" %>

</body>

</html>