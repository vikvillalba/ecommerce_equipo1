/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ProductoDAO;
import entidades.Producto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Maryr
 */
@WebServlet(name = "BusquedaServlet", urlPatterns = {"/BusquedaServlet"})
public class BusquedaServlet extends HttpServlet {

    private ProductoDAO productoDAO = ProductoDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String termino = req.getParameter("termino");
        if (termino == null || termino.trim().isEmpty()) {
            resp.getWriter().write("[]");
            return;
        }
        try {
            List<Producto> todosProductos = productoDAO.listar();
            String terminoLower = termino.toLowerCase().trim();
            StringBuilder json = new StringBuilder("[");
            int contador = 0;
            for (Producto p : todosProductos) {
                if (contador >= 5) {
                    break;
                }
                String nombre = p.getNombre().toLowerCase();
                String categoria = "";
                if (p.getCategoria() != null) {
                    categoria = p.getCategoria().getNombre().toLowerCase();
                }
                if (nombre.contains(terminoLower) || categoria.contains(terminoLower)) {
                    if (contador > 0) {
                        json.append(",");
                    }
                    json.append("{");
                    json.append("\"id\":").append(p.getId()).append(",");
                    json.append("\"nombre\":\"").append(p.getNombre().replace("\"", "\\\"")).append("\",");
                    json.append("\"precio\":").append(p.getPrecio()).append(",");
                    json.append("\"imagen\":\"").append(p.getImagen().replace("\"", "\\\"")).append("\"");
                    json.append("}");
                    contador++;
                }
            }
            json.append("]");
            resp.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("[]");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la busqueda de productos.";
    }

}
