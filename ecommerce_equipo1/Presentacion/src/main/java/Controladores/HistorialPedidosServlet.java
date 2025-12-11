/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.PedidoDAO;
import Exceptions.PersistenciaException;
import entidades.Pedido;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet encargado de mostrar el historial de pedidos del cliente actual.
 *
 * @author erika
 */
@WebServlet(name = "HistorialPedidosServlet", urlPatterns = {"/HistorialPedidost"})
public class HistorialPedidosServlet extends HttpServlet {

    private PedidoDAO pedidoDAO = PedidoDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // Obtener la sesión existente

        if (session != null) {
            // Obtener el correo del cliente desde la sesión
            String correoCliente = (String) session.getAttribute("usuarioCorreo");

            if (correoCliente != null) {
                try {
                    // Consultar la lista de pedidos usando el correo
                    List<Pedido> pedidos = pedidoDAO.obtenerPedidosUsuario(correoCliente);

                    if (pedidos == null) {
                        pedidos = new ArrayList<>();
                    }

                    // Establecer la lista de pedidos como atributo de la solicitud
                    req.setAttribute("pedidos", pedidos);

                } catch (PersistenciaException e) {
                    // Manejo de error si falla la consulta a la BD
                    req.setAttribute("error", "No se pudo cargar el historial de pedidos: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                // Si el correo no está en sesión
                req.setAttribute("error", "Debes iniciar sesión para ver tu historial.");
            }
        } else {
            // Si no hay sesión, tratar como no logueado
            req.setAttribute("error", "Debes iniciar sesión para ver tu historial.");
        }

        // 4. Enviar la solicitud al JSP para mostrar los datos 
        req.getRequestDispatcher("historialPedidos.jsp").forward(req, resp);
    }
}
