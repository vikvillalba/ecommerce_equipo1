/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.PedidoDAO;
import entidades.Pedido;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erika
 */
@WebServlet(name = "HistorialPedidosServlet", urlPatterns = {"/HistorialPedidost"})
public class HistorialPedidosServlet extends HttpServlet {

    private PedidoDAO pedidoDAO = PedidoDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        List<Pedido> pedidos = pedidoDAO.obtenerPedidosUsuario(1);
//
//        if (pedidos == null) {
//            pedidos = new ArrayList<>();
//        }
//
//        req.setAttribute("pedidos", pedidos);
        req.getRequestDispatcher("historialPedidos.jsp").forward(req, resp);
    }
}
