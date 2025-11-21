/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DAOs.ClienteDAO;
import entidades.Cliente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Maryr
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Mostrar la página de login
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Obtener datos del formulario
        String correo = req.getParameter("correo");
        String contrasena = req.getParameter("contrasena");

        // Validar que los campos no estén vacíos
        if (correo == null || correo.trim().isEmpty()
                || contrasena == null || contrasena.trim().isEmpty()) {
            req.setAttribute("error", "Por favor completa todos los campos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // Intentar autenticar al usuario
        Cliente cliente = clienteDAO.autenticar(correo, contrasena);

        if (cliente != null) {
            // Login exitoso - crear sesión
            HttpSession session = req.getSession();
            session.setAttribute("cliente", cliente);
            session.setAttribute("clienteId", cliente.getId());
            session.setAttribute("clienteNombre", cliente.getNombre());

            // Redirigir al catálogo
            resp.sendRedirect("Catalogo");
        } else {
            // Login fallido
            req.setAttribute("error", "Correo o contraseña incorrectos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para autenticación de usuarios";
    }
}
