package Controladores;

import DTOs.ProductoCarritoDTO;
import Modelos.CarritoBO;
import enums.Tallas;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet para manejar las operaciones del Carrito de Compras, persistente solo
 * en la sesión.
 *
 * @author Alici
 */
@WebServlet(name = "CarritoServlet", urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {

    private final CarritoBO carritoBO = new CarritoBO();
    private static final Logger LOGGER = Logger.getLogger(CarritoServlet.class.getName());

    /**
     * Handles the HTTP <code>GET</code> method. Muestra el carrito de la
     * sesión.
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
        req.getRequestDispatcher("/carrito.jsp").forward(req, res);
    }

    /**
     * Handles the HTTP <code>POST</code> method. Maneja las acciones de
     * agregar, actualizar y eliminar productos.
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
        String accion = request.getParameter("accion");

        try {
            if ("agregar".equals(accion)) {
                String productoIdStr = request.getParameter("idProducto");
                String cantidadStr = request.getParameter("cantidad");
                String tallaStr = request.getParameter("talla");
                
                if (productoIdStr == null || cantidadStr == null || tallaStr == null || tallaStr.isEmpty()) {
                    throw new IllegalArgumentException("Faltan parámetros requeridos (ID, Cantidad o Talla).");
                }

                Integer idProducto = Integer.parseInt(productoIdStr);
                int cantidad = Integer.parseInt(cantidadStr);
                Tallas talla = Tallas.valueOf(tallaStr.toUpperCase().replace(" ", "_"));
                
                // Obtener datos ocultos del formulario 
                String nombreProducto = request.getParameter("nombreProducto");
                double precio = Double.parseDouble(request.getParameter("precio"));
                String direccionImagen = request.getParameter("direccionImagen");
                String colorHex = request.getParameter("colorHex");

                if (cantidad <= 0) {
                     throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
                }

                // Crear el DTO
                ProductoCarritoDTO nuevoProducto = new ProductoCarritoDTO();
                nuevoProducto.setIdProducto(idProducto);
                nuevoProducto.setNombreProducto(nombreProducto);
                nuevoProducto.setTalla(talla);
                nuevoProducto.setCantidad(cantidad);
                nuevoProducto.setPrecio(precio); // El DTO calcula el subtotal automáticamente
                nuevoProducto.setDireccionImagen(direccionImagen);
                nuevoProducto.setColorHex(colorHex);

                // Agregar al Carrito
                carritoBO.agregarProducto(session, nuevoProducto);
                
                response.sendRedirect(request.getContextPath() + "/carrito");
                return;

            } else {
                String nombreProducto = request.getParameter("nombreProducto");
                String tallaStr = request.getParameter("talla"); // Ahora es tallaStr

                if (tallaStr == null || nombreProducto.isEmpty() || nombreProducto == null) {
                    response.sendRedirect("carrito");
                    return;
                }

                Tallas tallaEnum = Tallas.valueOf(tallaStr.toUpperCase().replace(" ", "_"));

                if ("actualizar".equals(accion)) {
                    String nuevaCantidadStr = request.getParameter("nuevaCantidad");
                    if (nuevaCantidadStr != null && !nuevaCantidadStr.isEmpty()) {
                        int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);

                        if (nuevaCantidad >= 1) {
                            carritoBO.actualizarCantidadProducto(session, nombreProducto, tallaEnum, nuevaCantidad);
                        }
                    }

                } else if ("eliminar".equals(accion)) {
                    carritoBO.eliminarProducto(session, nombreProducto, tallaEnum);
                }
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error de formato de número al procesar el carrito.", e);
            request.setAttribute("errorMensaje", "Error en el formato de los datos de cantidad o ID.");
            if("agregar".equals(accion)) {
                 response.sendRedirect(request.getContextPath() + "/producto.jsp?error=true");
                 return;
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Error de parámetros de carrito.", e);
            request.setAttribute("errorMensaje", e.getMessage());
            if("agregar".equals(accion)) {
                 response.sendRedirect(request.getContextPath() + "/producto.jsp?error=true");
                 return;
            }
        }

        // Redirigir a la página del carrito después de Actualizar/Eliminar
        response.sendRedirect("carrito");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Carrito Servlet que maneja la lógica de la sesión.";
    }// </editor-fold>

}