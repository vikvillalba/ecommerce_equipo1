/*
 * Servlet para administrar categorías
 */
package Controladores;

import DAOs.CategoriaDAO;
import DTOs.CategoriaDTO;
import Exceptions.ModeloException;
import Modelos.CategoriaBO;
import entidades.Categoria;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack Murrieta
 */
@WebServlet(name = "AdminCategoriaServlet", urlPatterns = {"/AdminCategoriaServlet"})
public class AdminCategoriaServlet extends HttpServlet {

    private final CategoriaBO categoriaBO = new CategoriaBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<CategoriaDTO> categorias = categoriaBO.obtenerCategorias();
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("adminCategorias.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String accion = req.getParameter("accion");
        String nombre = req.getParameter("nombre");

        if (accion != null) {
            switch (accion) {
                case "agregar":
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        CategoriaDTO nueva = new CategoriaDTO();
                        nueva.setNombre(nombre.toUpperCase().trim());
                        nueva.setActiva(true);
                        try {
                            categoriaBO.agregarCategoria(nueva);
                        } catch (ModeloException ex) {
                            Logger.getLogger(AdminCategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case "eliminar":
                    if (nombre != null) {
                        try {
                            categoriaBO.eliminarCategoria(nombre);
                        } catch (ModeloException ex) {
                            Logger.getLogger(AdminCategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case "desactivar":
                    if (nombre != null) {
                        try {
                            categoriaBO.desactivarCategoria(nombre);
                        } catch (ModeloException ex) {
                            Logger.getLogger(AdminCategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case "activar":
                    if (nombre != null) {
                        try {
                            categoriaBO.activarCategoria(nombre);
                        } catch (ModeloException ex) {
                            Logger.getLogger(AdminCategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
            }
        }

        resp.sendRedirect("AdminCategoriaServlet");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para administrar categorías del sistema";
    }
}
