package Controladores;

import DTOs.PedidoDTO;
import Exceptions.ModeloException;
import Modelos.PedidoBO;
import enums.EstadoPedido;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet encargado de la gestión y visualización de pedidos. Procesa
 * peticiones GET para mostrar la lista de pedidos y peticiones POST para
 * actualizar el estado de un pedido. Mapeado a la URL "/pedidos".
 *
 * @author pablo
 */
@WebServlet(name = "PedidosServlet", urlPatterns = {"/pedidos"})
public class PedidosServlet extends HttpServlet {

    /**
     * Objeto de negocio (Business Object) para interactuar con la lógica de
     * pedidos.
     */
    private PedidoBO pedidoBO = new PedidoBO();

    /**
     * Procesa las solicitudes HTTP GET. Se utiliza para obtener la lista
     * completa de pedidos y enviarla a la vista pedidos.jsp.
     *
     * @param request El objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param response El objeto HttpServletResponse que contiene la respuesta
     * que el servlet envía al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada o salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PedidoDTO> lista = new ArrayList<>();
        try {
            lista = pedidoBO.obtenerPedidos();
        } catch (ModeloException ex) {
            Logger.getLogger(PedidosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("pedidosLista", lista);
        request.getRequestDispatcher("pedidos.jsp").forward(request, response);
    }

    /**
     * Procesa las solicitudes HTTP POST. Se utiliza para recibir un
     * identificador de pedido y un nuevo estado, actualizando el estado de
     * dicho pedido en el sistema.
     *
     * @param request El objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param response El objeto HttpServletResponse que contiene la respuesta
     * que el servlet envía al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada o salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numero = request.getParameter("idPedido");
        String estadoStr = request.getParameter("estado");
        EstadoPedido nuevoEstado = EstadoPedido.valueOf(estadoStr.toUpperCase());
        try {
            pedidoBO.actualizarEstado(numero, nuevoEstado);
        } catch (ModeloException ex) {
            Logger.getLogger(PedidosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("pedidos");
    }

    /**
     * Retorna una breve descripción del servlet.
     *
     * @return Una cadena que contiene la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para mostrar los pedidos";
    }

}
