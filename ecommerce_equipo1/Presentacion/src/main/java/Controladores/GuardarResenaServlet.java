/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ProductoDAO;
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

/**
 *
 * @author erika
 */
@WebServlet("/GuardarResenaServlet")
public class GuardarResenaServlet extends HttpServlet {

    private ProductoDAO dao = ProductoDAO.getInstancia();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer idProducto = Integer.parseInt(req.getParameter("idProducto"));
        String comentario = req.getParameter("comentario");
        int calificacion = Integer.parseInt(req.getParameter("calificacion"));

        Producto p = dao.obtenerPorId(idProducto);

        if (p == null) {
            resp.sendError(404, "Producto no encontrado");
            return;
        }

        Resena r = new Resena();
        r.setId((int) (Math.random() * 10000));
        r.setComentario(comentario);
        r.setCalificacion(calificacion);

        Cliente c = new Cliente();
        c.setNombre("Usuario Anónimo");
        r.setCliente(c);

        p.getResenas().add(r);

        resp.sendRedirect("ResenasServlet?id=" + idProducto);
    }
}

