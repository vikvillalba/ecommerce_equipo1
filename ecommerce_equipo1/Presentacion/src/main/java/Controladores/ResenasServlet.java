/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ClienteDAO;
import DAOs.ResenaDAO;
import DAOs.ProductoDAO;
import entidades.Cliente;
import entidades.Resena;
import entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author erika
 */
@WebServlet(name = "ResenasServlet", urlPatterns = {"/ResenasServlet"})
public class ResenasServlet extends HttpServlet {

    private ProductoDAO dao = ProductoDAO.getInstancia();
    private final ResenaDAO resenaDAO = new ResenaDAO();
    private final ClienteDAO clienteDAO = ClienteDAO.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        Producto producto = dao.obtenerPorId(id);

        if (producto == null) {
            resp.sendError(404, "Producto no encontrado");
            return;
        }

        req.setAttribute("producto", producto);
        req.getRequestDispatcher("resenas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        Integer clienteId = (Integer) session.getAttribute("usuarioId");

        String comentario = req.getParameter("comentario");
        String calificacionStr = req.getParameter("calificacion");
        String productoIdStr = req.getParameter("productoId");

        if (clienteId == null || comentario == null || calificacionStr == null || productoIdStr == null) {
            resp.sendError(400, "Datos incompletos para la reseña.");
            return;
        }

        Integer productoId = Integer.parseInt(productoIdStr);
        Integer calificacion = Integer.parseInt(calificacionStr);

        Cliente cliente = clienteDAO.obtenerPorId(clienteId);
        Producto producto = dao.obtenerPorId(productoId);

        if (cliente == null || producto == null) {
            resp.sendError(404, "Cliente o Producto no encontrado para la reseña.");
            return;
        }

        Resena nuevaResena = new Resena();

        nuevaResena.setCliente(cliente);
        nuevaResena.setProducto(producto);
        nuevaResena.setComentario(comentario);
        nuevaResena.setCalificacion(calificacion);

        boolean guardado = resenaDAO.crearResena(nuevaResena);

        if (guardado) {
            resp.sendRedirect(req.getContextPath() + "/ResenasServlet?id=" + productoId);
        } else {
            req.setAttribute("error", "Error al guardar la reseña. Intente de nuevo.");
            req.setAttribute("producto", producto);
            req.getRequestDispatcher("resenas.jsp").forward(req, resp);
        }
    }
}
