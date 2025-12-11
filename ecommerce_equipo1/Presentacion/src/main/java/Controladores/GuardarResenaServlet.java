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
 * **Servlet GuardarResenaServlet**
 *
 * Este Servlet maneja la solicitud HTTP POST para procesar y persistir una
 * nueva reseña enviada por un cliente para un producto específico.
 *
 * @author erika
 */
@WebServlet("/GuardarResenaServlet")
public class GuardarResenaServlet extends HttpServlet {

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Producto.
     */
    private final IProductoDAO productoDAO = ProductoDAO.getInstancia();

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Resena.
     */
    private final IResenaDAO resenaDAO = new ResenaDAO();

    /**
     * Instancia del DAO para operaciones relacionadas con la entidad Cliente.
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstancia();

    /**
     * Maneja las solicitudes HTTP POST, utilizadas para el envío de
     * formularios.
     *
     * Se encarga de: 1. Recoger y validar los parámetros del formulario (ID de
     * producto, calificación, comentario). 2. Recuperar las entidades Producto
     * y Cliente (de la sesión). 3. Construir la entidad Resena. 4. Persistir la
     * reseña usando el DAO. 5. Redirigir al usuario (éxito) o mostrar mensaje
     * de error.
     *
     * @param req Objeto HttpServletRequest que contiene la solicitud del
     * cliente.
     * @param resp Objeto HttpServletResponse que contiene la respuesta que el
     * servlet enviará.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Integer idProducto = null;
        Integer calificacion = null;
        String comentario = req.getParameter("comentario");

        //Obtención y Validación de Parámetros
        try {
            // Intenta convertir los parámetros a Integers.
            idProducto = Integer.parseInt(req.getParameter("idProducto"));
            calificacion = Integer.parseInt(req.getParameter("calificacion"));
        } catch (NumberFormatException e) {
            // Si la conversión falla, los datos son inválidos (Error 400).
            resp.sendError(400, "ID de Producto o Calificación inválida.");
            return;
        }

        // Obtención de la Entidad Producto 
        Producto p = productoDAO.obtenerPorId(idProducto);
        if (p == null) {
            // Si el producto no existe, devuelve un error 404.
            resp.sendError(404, "Producto no encontrado.");
            return;
        }

        // Obtención de la Entidad Cliente (de la Sesión)
        Cliente cliente = null;
        // Obtiene la sesión sin crear una nueva si no existe
        HttpSession session = req.getSession(false);
        Integer clienteId = (session != null) ? (Integer) session.getAttribute("usuarioId") : null;

        // Si hay un ID de cliente en la sesión, se busca la entidad completa en la base de datos.
        if (clienteId != null) {
            cliente = clienteDAO.obtenerPorId(clienteId);
        }


        // Creación de la Entidad Resena
        Resena r = new Resena();

        r.setComentario(comentario);
        r.setCalificacion(calificacion);
        r.setProducto(p);
        r.setCliente(cliente); 

        // Persistencia de la Reseña 
        boolean guardado = resenaDAO.crearResena(r);

        //Manejo de la Respuesta
        if (guardado) {
            // Éxito: Redirige al usuario a la página de reseñas del producto recién reseñado.
            // Se usa getContextPath() para asegurar la ruta absoluta.
            resp.sendRedirect(req.getContextPath() + "/ResenasServlet?id=" + idProducto);
        } else {
            // Fallo: Vuelve a mostrar el formulario de reseña con un mensaje de error.
            req.setAttribute("error", "Error al guardar la reseña. Intente de nuevo.");
            req.setAttribute("producto", p); // Vuelve a adjuntar el producto para el JSP.
            req.getRequestDispatcher("escribirResena.jsp").forward(req, resp);
        }
    }
}
