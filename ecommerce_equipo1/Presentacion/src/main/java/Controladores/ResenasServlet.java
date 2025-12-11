/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import DAOs.ClienteDAO;
import DAOs.ResenaDAO;
import DAOs.ProductoDAO;
import Interfaces.IClienteDAO;
import Interfaces.IProductoDAO;
import Interfaces.IResenaDAO;
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
 * **Servlet ResenasServlet**
 *
 * Este Servlet tiene dos funciones principales: 1. GET: Mostrar la página de
 * reseñas para un producto específico, cargando sus datos. 2. POST:
 * (Funcionalidad duplicada por GuardarResenaServlet) Procesar y persistir una
 * nueva reseña enviada por un cliente logueado.

 *
 * @author erika
 */
@WebServlet(name = "ResenasServlet", urlPatterns = {"/ResenasServlet"})
public class ResenasServlet extends HttpServlet {

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
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstancia();

    /**
     * Maneja las solicitudes HTTP GET.
     *
     * Se encarga de cargar y mostrar la página con todas las reseñas de un
     * producto. 1. Recupera el ID del producto de los parámetros de la
     * solicitud. 2. Busca el producto en la base de datos. 3. Si el producto
     * existe, lo establece como atributo. 4. Reenvía al JSP `resenas.jsp`.
     *
     * @param req Objeto HttpServletRequest que contiene la solicitud del
     * cliente (debe incluir el parámetro 'id').
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

        //Busca el objeto Producto por su ID.
        Producto producto = dao.obtenerPorId(id);

        //Verifica si el producto fue encontrado.
        if (producto == null) {
            resp.sendError(404, "Producto no encontrado");
            return;
        }

        //Si se encuentra, establece el objeto 'producto' como atributo
        req.setAttribute("producto", producto);

        //Reenvía la solicitud al JSP para mostrar las reseñas.
        req.getRequestDispatcher("resenas.jsp").forward(req, resp);
    }

    /**
     * Maneja las solicitudes HTTP POST.
     *
     * Este método procesa el envío de una nueva reseña y la persiste. Nota: La
     * lógica es similar a GuardarResenaServlet. 1. Valida que el cliente esté
     * logueado y que los datos de la reseña estén completos. 2. Convierte ID y
     * calificación a enteros. 3. Recupera las entidades Cliente y Producto. 4.
     * Crea y persiste la entidad Resena. 5. Redirige a la página de reseñas
     * actualizada o maneja el error.
     *
     * @param req Objeto HttpServletRequest que contiene la solicitud del
     * formulario.
     * @param resp Objeto HttpServletResponse que contiene la respuesta que el
     * servlet enviará.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Intenta recuperar la sesión existente.
        HttpSession session = req.getSession(false);
        Integer clienteId = (Integer) session.getAttribute("usuarioId");

        // Parámetros recibidos del formulario
        String comentario = req.getParameter("comentario");
        String calificacionStr = req.getParameter("calificacion");
        String productoIdStr = req.getParameter("productoId");

        // Validación de datos de sesión y formulario
        if (clienteId == null || comentario == null || calificacionStr == null || productoIdStr == null) {
            // Usuario no logueado o datos del formulario incompletos.
            resp.sendError(400, "Datos incompletos para la reseña (cliente no logueado o campos vacíos).");
            return;
        }

        // Conversión de Strings a Integers
        Integer productoId = Integer.parseInt(productoIdStr);
        Integer calificacion = Integer.parseInt(calificacionStr);

        // Recuperación de Entidades
        Cliente cliente = clienteDAO.obtenerPorId(clienteId);
        Producto producto = dao.obtenerPorId(productoId);

        // Validación de existencia de entidades
        if (cliente == null || producto == null) {
            resp.sendError(404, "Cliente o Producto no encontrado para la reseña.");
            return;
        }

        // Creación de la Entidad Resena
        Resena nuevaResena = new Resena();

        nuevaResena.setCliente(cliente);
        nuevaResena.setProducto(producto);
        nuevaResena.setComentario(comentario);
        nuevaResena.setCalificacion(calificacion);

        // Persistencia
        boolean guardado = resenaDAO.crearResena(nuevaResena);

        // Manejo de Respuesta
        if (guardado) {
            // Éxito: Redirige para mostrar la lista de reseñas actualizada.
            resp.sendRedirect(req.getContextPath() + "/ResenasServlet?id=" + productoId);
        } else {
            // Fallo: Vuelve al JSP mostrando el mensaje de error.
            req.setAttribute("error", "Error al guardar la reseña. Intente de nuevo.");
            req.setAttribute("producto", producto);
            req.getRequestDispatcher("resenas.jsp").forward(req, resp);
        }
    }
}
