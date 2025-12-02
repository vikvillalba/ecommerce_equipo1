/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ClienteDAO;
import entidades.Cliente;
import entidades.Direccion;
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
@WebServlet(name = "ModificarPerfilServlet", urlPatterns = {"/ModificarPerfilServlet"})
public class ModificarPerfilServlet extends HttpServlet {

    private ClienteDAO clienteDAO = ClienteDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        Object usuario = session.getAttribute("usuario");

        // Verificar que hay sesión y que el usuario es un Cliente
        if (session == null || usuario == null || !(usuario instanceof Cliente)) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        req.getRequestDispatcher("modificarPerfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        // Obtener el usuario de la sesión
        Object usuario = session.getAttribute("usuario");
        if (!(usuario instanceof Cliente)) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        Cliente cliente = (Cliente) usuario;

        String nombre = req.getParameter("nombre");
        String telefono = req.getParameter("telefono");
        String contrasena = req.getParameter("contrasena");
        String confirmaContrasena = req.getParameter("confirmaContrasena");
        String codigoPostal = req.getParameter("codigoPostal");
        String colonia = req.getParameter("colonia");
        String calle = req.getParameter("calle");
        String numero = req.getParameter("numero");

        if (nombre == null || nombre.trim().isEmpty()) {
            req.setAttribute("error", "El nombre es obligatorio");
            req.getRequestDispatcher("modificarPerfil.jsp").forward(req, resp);
            return;
        }

        // Validar contraseña
        if (contrasena != null && !contrasena.trim().isEmpty()) {
            if (confirmaContrasena == null || !contrasena.equals(confirmaContrasena)) {
                req.setAttribute("error", "Las contraseñas no coinciden");
                req.getRequestDispatcher("modificarPerfil.jsp").forward(req, resp);
                return;
            }
            
            cliente.setContrasena(contrasena);
        }

        // Actualizar datos
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);

        // Actualizar dirección
        Direccion direccion = cliente.getDireccion();
        if (direccion == null) {
            direccion = new Direccion();
        }

        direccion.setCodigoPostal(codigoPostal);
        direccion.setColonia(colonia);
        direccion.setCalle(calle);
        direccion.setNumero(numero);

        cliente.setDireccion(direccion);

        boolean actualizado = clienteDAO.actualizar(cliente);

        if (actualizado) {
            // Actualizar en la sesión
            session.setAttribute("usuario", cliente);
            session.setAttribute("cliente", cliente);
            session.setAttribute("usuarioNombre", cliente.getNombre());

            req.setAttribute("success", "Datos actualizados correctamente");
            req.getRequestDispatcher("modificarPerfil.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Error al actualizar los datos. Intenta nuevamente.");
            req.getRequestDispatcher("modificarPerfil.jsp").forward(req, resp);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para modificar el perfil del cliente";
    }
}
