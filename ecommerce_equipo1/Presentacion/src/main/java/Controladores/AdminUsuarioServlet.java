/*
 * Servlet para administrar usuarios/clientes
 */
package Controladores;

import DAOs.ClienteDAO;
import entidades.Cliente;
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
@WebServlet(name = "AdminUsuarioServlet", urlPatterns = {"/AdminUsuarioServlet"})
public class AdminUsuarioServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Cliente> usuarios = clienteDAO.listar();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("adminCuentas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String accion = req.getParameter("accion");
        String idParam = req.getParameter("id");

        if (idParam != null && accion != null) {
            try {
                Integer id = Integer.parseInt(idParam);

                switch (accion) {
                    case "eliminar":
                        clienteDAO.eliminar(id);
                        break;
                    case "desactivar":
                        clienteDAO.desactivar(id);
                        break;
                    case "activar":
                        clienteDAO.activar(id);
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect("AdminUsuarioServlet");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para administrar usuarios del sistema";
    }
}
