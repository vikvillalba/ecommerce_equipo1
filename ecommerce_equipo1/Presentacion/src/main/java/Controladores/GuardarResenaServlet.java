/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ClienteDAO;
import DAOs.ProductoDAO;
import DAOs.ResenaDAO;
import entidades.Cliente;
import entidades.Producto;
import entidades.Resena;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author erika
 */
@WebServlet("/GuardarResenaServlet")
public class GuardarResenaServlet extends HttpServlet {

    private final ProductoDAO productoDAO = ProductoDAO.getInstancia();
    private final ResenaDAO resenaDAO = new ResenaDAO();
    private final ClienteDAO clienteDAO = ClienteDAO.getInstancia();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        
        Integer idProducto = null;
        Integer calificacion = null;
        String comentario = req.getParameter("comentario");
        

        try {
            idProducto = Integer.parseInt(req.getParameter("idProducto"));
            calificacion = Integer.parseInt(req.getParameter("calificacion"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID de Producto o Calificación inválida.");
            return;
        }

        Producto p = productoDAO.obtenerPorId(idProducto);
        if (p == null) {
            resp.sendError(404, "Producto no encontrado.");
            return;
        }

        Cliente cliente = null;
        HttpSession session = req.getSession(false);
        Integer clienteId = (session != null) ? (Integer) session.getAttribute("usuarioId") : null;
        
        if (clienteId != null) {
            cliente = clienteDAO.obtenerPorId(clienteId);
        }

        Resena r = new Resena();

        r.setComentario(comentario);
        r.setCalificacion(calificacion);
        r.setProducto(p);
        r.setCliente(cliente); 

        boolean guardado = resenaDAO.crearResena(r);

        if (guardado) {

            resp.sendRedirect(req.getContextPath() + "/ResenasServlet?id=" + idProducto);
        } else {

            req.setAttribute("error", "Error al guardar la reseña. Intente de nuevo.");
            req.setAttribute("producto", p); 
            req.getRequestDispatcher("escribirResena.jsp").forward(req, resp);
        }
    }
}

