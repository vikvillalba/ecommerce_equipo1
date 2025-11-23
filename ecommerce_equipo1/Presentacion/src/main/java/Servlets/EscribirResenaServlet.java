/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DAOs.ProductoDAO;
import entidades.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author erika
 */
@WebServlet(name = "EscribirResenaServlet", urlPatterns = {"/EscribirResenaServlet"})
public class EscribirResenaServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        Producto producto = dao.obtenerPorId(id);

        if (producto == null) {
            resp.sendError(404, "Producto no encontrado");
            return;
        }

        req.setAttribute("producto", producto);
        req.getRequestDispatcher("escribirResena.jsp").forward(req, resp);
    }
}
