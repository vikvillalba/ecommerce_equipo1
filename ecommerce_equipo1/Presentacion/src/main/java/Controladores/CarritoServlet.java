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
     * actualizar y eliminar productos.
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
        String nombreProducto = request.getParameter("nombreProducto");
        String talla = request.getParameter("talla");

        if (talla == null || nombreProducto.isEmpty() || nombreProducto == null) {
            response.sendRedirect("carrito");
            return;
        }

        try {
            int idProducto = Integer.parseInt(talla);

            if ("actualizar".equals(accion)) {
                String nuevaCantidadStr = request.getParameter("nuevaCantidad");
                if (nuevaCantidadStr != null && !nuevaCantidadStr.isEmpty()) {
                    int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);

                    if (nuevaCantidad >= 1) {
                        carritoBO.actualizarCantidadProducto(session, nombreProducto, (Tallas.valueOf(talla)), nuevaCantidad);
                    }
                }

            } else if ("eliminar".equals(accion)) {
                carritoBO.eliminarProducto(session, nombreProducto, (Tallas.valueOf(talla)));
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error de formato de número al procesar el carrito.", e);
        }

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
