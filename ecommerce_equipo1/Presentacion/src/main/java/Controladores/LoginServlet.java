/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.UsuarioDAO;
import DAOs.ClienteDAO;
import entidades.Usuario;
import entidades.Cliente;
import enums.TipoUsuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Maryr
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    //autentica el cliente o admin
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ClienteDAO clienteDAO = ClienteDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String correo = req.getParameter("correo");
        String contrasena = req.getParameter("contrasena");

        // Validar que los campos no estén vacíos
        if (correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            req.setAttribute("error", "Por favor completa todos los campos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        Usuario usuario = usuarioDAO.autenticar(correo, contrasena);

        HttpSession session = req.getSession();

        if (usuario != null) {
            // Redirigir según el tipo de usuario
            if (usuario instanceof entidades.Administrador) {
                // Para administrador, guardar el Usuario directamenteç

                session.setAttribute("usuario", usuario);
                session.setAttribute("usuarioId", usuario.getId());
                session.setAttribute("usuarioNombre", usuario.getNombre());
                session.setAttribute("usuarioCorreo", usuario.getCorreo());
                session.setAttribute("tipoUsuario", TipoUsuario.ADMINISTRADOR);

                session.setAttribute("rol", "ADMINISTRADOR");

                resp.sendRedirect(req.getContextPath() + "/admin/AdminUsuarioServlet");
            } else if (usuario instanceof entidades.Cliente) {
                
                Cliente cliente = clienteDAO.obtenerPorUsuarioId(usuario.getId());
                if (cliente == null) {
                    // Si el usuario es de tipo Cliente pero no hay un registro Cliente asociado:
                    req.setAttribute("error", "Error interno: Cuenta de cliente incompleta o faltante.");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                    return; // Terminar la ejecución aquí
                }

                session.setAttribute("usuario", cliente);
                session.setAttribute("cliente", cliente);
                session.setAttribute("usuarioId", cliente.getId());
                session.setAttribute("usuarioNombre", cliente.getNombre());
                session.setAttribute("usuarioCorreo", cliente.getCorreo());
                session.setAttribute("tipoUsuario", TipoUsuario.CLIENTE);

                session.setAttribute("rol", "CLIENTE");

                resp.sendRedirect(req.getContextPath() + "/CatalogoServlet");
            } else {
                req.setAttribute("error", "No se encontró el perfil de cliente asociado");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para autenticación de usuarios";
    }
}
