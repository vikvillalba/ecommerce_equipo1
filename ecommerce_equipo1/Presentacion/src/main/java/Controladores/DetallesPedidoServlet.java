package Controladores;

import DAOs.CompraDAO;
import DTOs.ProductoCarritoDTO;
import Modelos.CarritoBO;
import entidades.Cliente;
import entidades.Compra;
import entidades.Direccion;
import entidades.Pedido;
import entidades.ProductoCompra;
import entidades.Usuario;
import entidades.PagoTarjeta;
import entidades.PagoTransferencia;
import entidades.PagoContraEntrega;
import interfaces.MetodoPago;
import enums.EstadoPedido;
import enums.TipoMetodoPago;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet que maneja la vista y la finalización del pedido (checkout).
 *
 * @author Alici
 */
@WebServlet(name = "DetallesPedidoServlet", urlPatterns = {"/detallesPedido"})
@jakarta.servlet.annotation.MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class DetallesPedidoServlet extends HttpServlet {

    private final CarritoBO carritoBO = new CarritoBO();
    private final CompraDAO compraDAO = CompraDAO.getInstancia();
    private static final Pattern EXPIRATION_DATE_PATTERN = Pattern.compile("(\\d{2})/(\\d{2})"); // MM/YY
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    /**
     * Handles the HTTP <code>GET</code> method (Muestra el formulario de pago).
     *
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<ProductoCarritoDTO> productosCarrito = carritoBO.obtenerProductosCarrito(session);

        req.setAttribute("productosCarrito", productosCarrito);
        req.getRequestDispatcher("/detallesPago.jsp").forward(req, res);
    }

    /**
     * Handles the HTTP <code>POST</code> method (Procesa el pedido).
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        Usuario usuarioEnSession = (Usuario) session.getAttribute("usuario");

        if (!(usuarioEnSession instanceof Cliente)) {
            request.setAttribute("errorMensaje", "Debes iniciar sesión como Cliente para completar el pedido.");
            response.sendRedirect("login.jsp");
            return;
        }

        Cliente cliente = (Cliente) usuarioEnSession;

        try {
            request.setCharacterEncoding("UTF-8");

            List<ProductoCarritoDTO> productosCarritoDTO = carritoBO.obtenerProductosCarrito(session);

            if (productosCarritoDTO.isEmpty()) {
                request.setAttribute("errorMensaje", "Tu carrito de compras está vacío.");
                doGet(request, response);
                return;
            }

            // validacion del contacto
            String nombreCompleto = request.getParameter("nombre-completo");
            String telefono = request.getParameter("telefono");
            String correoElectronico = request.getParameter("correo-electronico");

            if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
                throw new Exception("El nombre completo del contacto es obligatorio.");
            }
            if (telefono == null || telefono.trim().isEmpty() || telefono.length() < 10) {
                throw new Exception("El teléfono es obligatorio y debe tener al menos 10 dígitos.");
            }
            if (correoElectronico == null || !EMAIL_PATTERN.matcher(correoElectronico).matches()) {
                throw new Exception("El correo electrónico es inválido.");
            }

            // lectura del total
            String totalStr = request.getParameter("totalCompra");
            double total;

            if (totalStr == null || totalStr.trim().isEmpty()) {
                total = productosCarritoDTO.stream().mapToDouble(ProductoCarritoDTO::getSubtotal).sum();
                if (total <= 0) {
                    throw new Exception("El total de la compra no fue recibido y el carrito no tiene valor.");
                }
            } else {
                total = Double.parseDouble(totalStr);
            }

            // validacion y creacion de direccion
            String calle = request.getParameter("calle");
            String numero = request.getParameter("numero");
            String colonia = request.getParameter("colonia");
            String codigoPostal = request.getParameter("codigo-postal");
            String pais = request.getParameter("pais");

            if (calle == null || calle.isEmpty() || numero == null || numero.isEmpty()
                    || colonia == null || colonia.isEmpty() || codigoPostal == null || codigoPostal.isEmpty()
                    || pais == null || pais.isEmpty() || "...".equals(pais)) {
                throw new Exception("Faltan campos obligatorios en la dirección de envío.");
            }

            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setNumero(numero);
            direccion.setColonia(colonia);
            direccion.setCodigoPostal(codigoPostal);
            direccion.setPais(pais);

            //  Determinar metodo de pago
            String metodoPagoStr = request.getParameter("metodo-pago");
            if (metodoPagoStr == null || metodoPagoStr.isEmpty()) {
                throw new Exception("Debe seleccionar un método de pago.");
            }
            TipoMetodoPago tipoMetodoPago = TipoMetodoPago.valueOf(metodoPagoStr);
            MetodoPago metodoPagoEntidad = null;

            //  Implementar logica de pago
            switch (tipoMetodoPago) {
                case TARJETA:
                    metodoPagoEntidad = crearPagoTarjeta(request);
                    break;
                case TRANSFERENCIA:
                    metodoPagoEntidad = crearPagoTransferencia(request);
                    break;
                case CONTRA_ENTREGA:
                    metodoPagoEntidad = new PagoContraEntrega();
                    break;
            }

            if (metodoPagoEntidad == null) {
                throw new Exception("Error al procesar los detalles del método de pago.");
            }

            // Crear Compra
            Compra compra = new Compra();
            compra.setCliente(cliente);
            compra.setFecha(Calendar.getInstance());
            compra.setTotal(total);
            compra.setMetodoPago(metodoPagoEntidad);

            //Crear la lista de ProductoCompra
            List<ProductoCompra> productosCompra = new ArrayList<>();
            for (ProductoCarritoDTO pcdto : productosCarritoDTO) {
                ProductoCompra pc = new ProductoCompra();
                pc.setCantidad(pcdto.getCantidad());
                pc.setPrecio(pcdto.getPrecio());
                pc.setSubtotal(pcdto.getSubtotal());
                pc.setCompra(compra);

                // Transferir la talla del DTO a la entidad de persistencia
                pc.setTalla(pcdto.getTalla());

                entidades.Producto productoRef = new entidades.Producto();
                productoRef.setId(pcdto.getIdProducto());
                productoRef.setNombre(pcdto.getNombreProducto());
                pc.setProducto(productoRef);

                productosCompra.add(pc);
            }
            compra.setProductos(productosCompra);

            // Crear el Pedido
            Pedido pedido = new Pedido();
            pedido.setDireccion(direccion);
            pedido.setCompra(compra);
            pedido.setEstado(EstadoPedido.PENDIENTE);

            // Generar el Pedido 
            Pedido pedidoConfirmado = compraDAO.generarPedido(pedido);

            //  Limpiar carrito de la sesión
            session.removeAttribute("productosCarrito");

            //  Preparar variables para la página de confirmación
            request.setAttribute("p", pedidoConfirmado);
            request.setAttribute("numeroOrden", pedidoConfirmado.getNumeroPedido());

            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

            request.setAttribute("fecha", sdf.format(pedidoConfirmado.getCompra().getFecha().getTime()));
            request.setAttribute("total", df.format(pedidoConfirmado.getCompra().getTotal()));
            request.setAttribute("metodoPago", pedidoConfirmado.getCompra().getTipoMetodoPago().toString().replace("_", " "));

            //  Redirigir a la página de confirmación
            request.getRequestDispatcher("/ordenCompleta.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMensaje", e.getMessage());

            List<ProductoCarritoDTO> productosCarrito = carritoBO.obtenerProductosCarrito(session);
            request.setAttribute("productosCarrito", productosCarrito);

            request.getRequestDispatcher("/detallesPago.jsp").forward(request, response);
        }
    }

    /**
     * Crea y popula la entidad PagoTarjeta desde los parámetros del request.
     *
     * @param request La solicitud HTTP.
     * @return PagoTarjeta completamente populado.
     */
    private PagoTarjeta crearPagoTarjeta(HttpServletRequest request) throws Exception {
        PagoTarjeta pagoTarjeta = new PagoTarjeta();

        String fechaCaducidadStr = request.getParameter("fecha-caducidad");

        // Validación de campos básicos
        if (request.getParameter("propietario-tarjeta") == null || request.getParameter("propietario-tarjeta").isEmpty()
                || request.getParameter("numero-tarjeta") == null || request.getParameter("numero-tarjeta").isEmpty()
                || request.getParameter("cvc") == null || request.getParameter("cvc").isEmpty()
                || fechaCaducidadStr == null || fechaCaducidadStr.isEmpty()) {
            throw new Exception("Faltan datos de la tarjeta de crédito.");
        }

        pagoTarjeta.setPropietario(request.getParameter("propietario-tarjeta"));
        pagoTarjeta.setNumeroCuenta(request.getParameter("numero-tarjeta"));
        pagoTarjeta.setCvv(request.getParameter("cvc"));

        // Validación y parseo de la fecha (MM/AA) a LocalDate
        Matcher matcher = EXPIRATION_DATE_PATTERN.matcher(fechaCaducidadStr);
        if (!matcher.matches()) {
            throw new Exception("Formato de fecha de caducidad inválido. Use MM/AA.");
        }

        int month = Integer.parseInt(matcher.group(1));
        int yearLastTwoDigits = Integer.parseInt(matcher.group(2));
        int currentYear = LocalDate.now().getYear();
        int century = (currentYear / 100) * 100;
        int fullYear = century + yearLastTwoDigits;

        if (month < 1 || month > 12) {
            throw new Exception("Mes de caducidad inválido.");
        }

        // Usamos el último día del mes para la validación
        LocalDate fechaExpiracion = LocalDate.of(fullYear, month, 1).withDayOfMonth(
                LocalDate.of(fullYear, month, 1).lengthOfMonth());

        if (fechaExpiracion.isBefore(LocalDate.now())) {
            throw new Exception("La tarjeta ha caducado.");
        }

        pagoTarjeta.setFechaExpiracion(fechaExpiracion);

        return pagoTarjeta;
    }

    /**
     * Crea y popula la entidad PagoTransferencia desde los parámetros del
     * request, leyendo el archivo de comprobante subido.
     *
     * @param request La solicitud HTTP.
     * @return PagoTransferencia completamente populado.
     */
    private PagoTransferencia crearPagoTransferencia(HttpServletRequest request) throws Exception {
        PagoTransferencia pagoTransferencia = new PagoTransferencia();

        // 1. Obtener la parte del archivo
        Part filePart = request.getPart("comprobante-transferencia");

        if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName().isEmpty()) {
            // El comprobante es obligatorio para este método
            throw new Exception("Debe subir un comprobante de transferencia (PDF, JPG, PNG).");
        }

        // 2. Leer los bytes del archivo
        try (InputStream fileContent = filePart.getInputStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            byte[] comprobanteBytes = bos.toByteArray();

            if (comprobanteBytes.length == 0) {
                throw new Exception("El archivo de comprobante está vacío.");
            }

            pagoTransferencia.setComprobante(comprobanteBytes);

        } catch (IOException e) {
            // Error de entrada/salida al leer el archivo
            throw new Exception("Error de lectura del archivo de comprobante: " + e.getMessage(), e);
        }

        return pagoTransferencia;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controlador para la finalización de la compra y creación de pedidos.";
    }// </editor-fold>

}
