/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DAOs.ClienteDAO;
import DAOs.UsuarioDAO;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Usuario;
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
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("mostrarRegistro", "true");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String nombre = req.getParameter("nombre");
        String telefono = req.getParameter("telefono");
        String correo = req.getParameter("correo");
        String contrasena = req.getParameter("contrasena");
        String estado = req.getParameter("estado");
        String pais = req.getParameter("pais");
        String ciudad = req.getParameter("ciudad");
        String codigoPostal = req.getParameter("codigoPostal");
        String colonia = req.getParameter("colonia");
        String calle = req.getParameter("calle");
        String numero = req.getParameter("numero");

        if (nombre == null || nombre.trim().isEmpty() || correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            req.setAttribute("error", "Por favor completa todos los campos obligatorios");
            req.setAttribute("mostrarRegistro", true);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // Verificar si el correo ya existe
        if (usuarioDAO.existeCorreo(correo)) {
            req.setAttribute("error", "El correo electrónico ya está registrado");
            req.setAttribute("mostrarRegistro", true);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // Crear el Usuario primero
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setTipoUsuario(TipoUsuario.CLIENTE);

        // Crear la dirección
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        direccion.setColonia(colonia);
        direccion.setCodigoPostal(codigoPostal);

        // Crear el Cliente y asociarle el Usuario y Dirección
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setUsuario(nuevoUsuario);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEstado(true);
        nuevoCliente.setDireccion(direccion);

        boolean registrado = clienteDAO.registrar(nuevoCliente);

        if (registrado) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", nuevoCliente);
            session.setAttribute("cliente", nuevoCliente);
            session.setAttribute("usuarioId", nuevoCliente.getId());
            session.setAttribute("usuarioNombre", nuevoCliente.getNombre());
            session.setAttribute("tipoUsuario", TipoUsuario.CLIENTE);
            resp.sendRedirect(req.getContextPath() + "/CatalogoServlet");
        } else {
            req.setAttribute("error", "Error al registrar el usuario. Intenta nuevamente.");
            req.setAttribute("mostrarRegistro", true);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registro de nuevos clientes";
    }
}
