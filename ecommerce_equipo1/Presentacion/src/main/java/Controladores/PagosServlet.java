/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.PedidoDAO;
import DTOs.CompraDTO;
import DTOs.PedidoDTO;
import Exceptions.ModeloException;
import Modelos.CompraBO;
import Modelos.PedidoBO;
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
 *
 * @author pablo
 */
@WebServlet(name = "PagosServlet", urlPatterns = {"/admin/pagos"})
public class PagosServlet extends HttpServlet {

    private PedidoBO pedidoBO = new PedidoBO();
    private CompraBO compraBO = new CompraBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CompraDTO> pagos = new ArrayList<>();
        try{
            List<PedidoDTO> lista = pedidoBO.obtenerPedidos();
            
            for (PedidoDTO pedido : lista) {
                CompraDTO pago = compraBO.obtenerCompraPedido(pedido.getNumeroPedido());
                pagos.add(pago);
            }
            
        }catch(ModeloException ex){
            Logger.getLogger(PedidosServlet.class.getName()).log(Level.SEVERE, "Error de validación en Compra: ", ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        request.setAttribute("pagos", pagos);
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

}
