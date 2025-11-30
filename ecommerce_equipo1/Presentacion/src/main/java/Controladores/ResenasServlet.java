/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ResenaDAO;
import DAOs.ProductoDAO;
import entidades.Resena;
import entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author erika
 */
@WebServlet(name = "ResenasServlet", urlPatterns = {"/ResenasServlet"})
public class ResenasServlet extends HttpServlet {

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
        req.getRequestDispatcher("resenas.jsp").forward(req, resp);
    }
}
