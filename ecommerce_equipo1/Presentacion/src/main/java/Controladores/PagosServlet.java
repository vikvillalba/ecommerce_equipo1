/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.PedidoDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author pablo
 */
@WebServlet(name = "PagosServlet", urlPatterns = {"/admin/Pagos"})
public class PagosServlet extends HttpServlet {

    private PedidoDAO pedidoDAO = PedidoDAO.getInstancia();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ////        List<Pedido> lista = pedidoDAO.obtenerPedidosUsuario(0);
//
//        List<Pedido> entregados = new ArrayList<>();
//
//        for (Pedido pedido : lista) {
//            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
//                entregados.add(pedido);
//            }
//        }
//
//        request.setAttribute("pagos", entregados);
        request.getRequestDispatcher("/admin/pagos.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
