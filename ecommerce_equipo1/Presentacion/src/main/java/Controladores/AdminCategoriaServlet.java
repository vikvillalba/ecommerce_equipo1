package Controladores;

import DTOs.CategoriaDTO;
import Exceptions.ModeloException;
import Modelos.CategoriaBO;
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
 * Servlet encargado de administrar las operaciones relacionadas con las
 * categorías, incluyendo la consulta, adición, eliminación y cambio de estado
 * (activar/desactivar). Mapeado a la URL "/AdminCategoriaServlet".
 *
 * @author Jack Murrieta
 */
@WebServlet(name = "AdminCategoriaServlet", urlPatterns = {"/admin/AdminCategoriaServlet"})
public class AdminCategoriaServlet extends HttpServlet {

    /**
     * Objeto de negocio (Business Object) para gestionar la lógica de las
     * categorías.
     */
    private final CategoriaBO categoriaBO = new CategoriaBO();

    /**
     * Procesa las solicitudes HTTP GET. Se utiliza para obtener y mostrar la
     * lista de categorías existentes en la página de administración.
     *
     * @param req El objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param resp El objeto HttpServletResponse que contiene la respuesta que
     * el servlet envía al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada o salida.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<CategoriaDTO> categorias = categoriaBO.obtenerCategorias();
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("/admin/adminCategorias.jsp").forward(req, resp);
    }

    /**
     * Procesa las solicitudes HTTP POST. Se utiliza para manejar las acciones
     * de administración como agregar, eliminar, activar y desactivar una
     * categoría.
     *
     * @param req El objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param resp El objeto HttpServletResponse que contiene la respuesta que
     * el servlet envía al cliente.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de entrada o salida.
     */
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

    /**
     * Retorna una breve descripción del servlet.
     *
     * @return Una cadena que contiene la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para administrar categorías del sistema";
    }
}
