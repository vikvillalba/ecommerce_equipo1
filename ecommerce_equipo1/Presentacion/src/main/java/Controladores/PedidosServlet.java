/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DTOs.PedidoDTO;
import Exceptions.ModeloException;
import Modelos.PedidoBO;
import enums.EstadoPedido;
import java.io.IOException;
import java.io.PrintWriter;
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
 *
 * @author pablo
 */
@WebServlet(name = "PedidosServlet", urlPatterns = {"/pedidos"})
public class PedidosServlet extends HttpServlet {

    private PedidoBO pedidoBO = new PedidoBO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PedidosServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PedidosServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para mostrar los pedidos";
    }

}
