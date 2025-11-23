/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DAOs.ClienteDAO;
import entidades.Cliente;
import entidades.Direccion;
import enums.TipoUsuario;
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
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

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

        if (clienteDAO.existeCorreo(correo)) {
            req.setAttribute("error", "El correo electrónico ya está registrado");
            req.setAttribute("mostrarRegistro", true);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        direccion.setColonia(colonia);
        direccion.setCodigoPostal(codigoPostal);
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEstado(true);
        nuevoCliente.setTipoUsuario(TipoUsuario.CLIENTE);
        nuevoCliente.setDireccion(direccion);

        boolean registrado = clienteDAO.registrar(nuevoCliente);

        if (registrado) {
            HttpSession session = req.getSession();
            session.setAttribute("cliente", nuevoCliente);
            session.setAttribute("clienteId", nuevoCliente.getId());
            session.setAttribute("clienteNombre", nuevoCliente.getNombre());
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
