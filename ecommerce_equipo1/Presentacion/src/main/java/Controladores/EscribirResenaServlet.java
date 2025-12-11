/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ClienteDAO;
import DAOs.ProductoDAO;
import DAOs.ResenaDAO;
import Interfaces.IClienteDAO;
import Interfaces.IProductoDAO;
import Interfaces.IResenaDAO;
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
 * **Servlet EscribirResenaServlet**
 *
 * Este Servlet gestiona la visualización del formulario para que un usuario
 * pueda escribir una reseña sobre un producto específico.

 *
 * @author erika
 */
@WebServlet(name = "EscribirResenaServlet", urlPatterns = {"/EscribirResenaServlet"})
public class EscribirResenaServlet extends HttpServlet {

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Producto.
     */
    private IProductoDAO dao = ProductoDAO.getInstancia();

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Resena.
     */
    private final IResenaDAO resenaDAO = new ResenaDAO();

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Cliente.
     * (Aunque no se usa directamente en este doGet, se mantiene por el contexto
     * original).
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstancia();

    /**
     * Maneja las solicitudes HTTP GET.
     *
     * Se encarga de: 1. Recuperar el ID del producto de los parámetros de la
     * solicitud. 2. Buscar el producto correspondiente en la base de datos. 3.
     * Si el producto existe, lo establece como un atributo de la solicitud. 4.
     * Reenvía la solicitud al JSP para mostrar el formulario de reseña.
     *
     * @param req Objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param resp Objeto HttpServletResponse que contiene la respuesta que el
     * servlet enviará.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtiene el ID del producto del parámetro 'id' de la URL.
        Integer id = Integer.parseInt(req.getParameter("id"));

        // Busca el objeto Producto por su ID utilizando el DAO.
        Producto producto = dao.obtenerPorId(id);

        //Verifica si el producto fue encontrado.
        if (producto == null) {
            // Si no se encuentra, devuelve un error 404 (No encontrado)
            resp.sendError(404, "Producto no encontrado");
            return;
        }

        // Si se encuentra, establece el objeto 'producto' como atributo
        // para que esté disponible en el JSP.
        req.setAttribute("producto", producto);

        // Reenvía la solicitud al JSP del formulario de reseña.
        req.getRequestDispatcher("escribirResena.jsp").forward(req, resp);
    }

}
