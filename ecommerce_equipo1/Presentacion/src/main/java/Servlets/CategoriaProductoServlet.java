/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import enums.Categoria;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author victoria
 */

@WebServlet(name = "CategoriaProductoServlet", urlPatterns = {"/categorias"})
public class CategoriaProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categorias", Categoria.values());

        String action = request.getParameter("action");
        if ("editar".equals(action)) {
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
        }
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Regresa las categorías existentes en el sistema";
    }// </editor-fold>

}
