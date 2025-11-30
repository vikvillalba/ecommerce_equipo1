/*
 * Servlet para administrar categorías
 */
package Controladores;

import DAOs.CategoriaDAO;
import entidades.Categoria;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Jack Murrieta
 */
@WebServlet(name = "AdminCategoriaServlet", urlPatterns = {"/AdminCategoriaServlet"})
public class AdminCategoriaServlet extends HttpServlet {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Categoria> categorias = categoriaDAO.listar();
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("adminCategorias.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String accion = req.getParameter("accion");
        String idParam = req.getParameter("id");
        String nombre = req.getParameter("nombre");

        if (accion != null) {
            switch (accion) {
                case "agregar":
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        Categoria nueva = new Categoria();
                        nueva.setNombre(nombre.toUpperCase().trim());
                        categoriaDAO.agregar(nueva);
                    }
                    break;
                case "eliminar":
                    if (idParam != null) {
                        try {
                            Integer id = Integer.parseInt(idParam);
                            categoriaDAO.eliminar(id);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "desactivar":
                    if (idParam != null) {
                        try {
                            Integer id = Integer.parseInt(idParam);
                            categoriaDAO.desactivar(id);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "activar":
                    if (idParam != null) {
                        try {
                            Integer id = Integer.parseInt(idParam);
                            categoriaDAO.activar(id);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
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
