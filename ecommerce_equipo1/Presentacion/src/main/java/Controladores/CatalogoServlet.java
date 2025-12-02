/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ProductoDAO;
import Exceptions.PersistenciaException;
import entidades.Producto;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author Maryr
 */
@WebServlet(name = "CatalogoServlet", urlPatterns = {"/CatalogoServlet"})
public class CatalogoServlet extends HttpServlet {

    private ProductoDAO productoDAO = ProductoDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            List<Producto> productos = productoDAO.listar();
            req.setAttribute("productos", productos);
            req.getRequestDispatcher("catalogo.jsp").forward(req, resp);
        } catch (PersistenciaException ex) {

            ex.printStackTrace();

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            resp.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("--- ERROR CRÍTICO DE PERSISTENCIA EN CATÁLOGO ---");
            out.println("CAUSA: " + ex.getMessage());
            out.println("TRAZA COMPLETA (CAUSA RAÍZ):");
            ex.getCause().printStackTrace(out); // Imprimir la causa raíz si existe
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para mostrar el catálogo de productos";
    }
}
