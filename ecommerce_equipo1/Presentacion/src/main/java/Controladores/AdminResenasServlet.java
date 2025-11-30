package Controladores;

import DAOs.ProductoDAO;
import DAOs.ResenaDAO;
import entidades.Producto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author victoria
 */
@WebServlet(name = "AdminResenasServlet", urlPatterns = {"/adminResenas"})
public class AdminResenasServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ResenaDAO dao = new ResenaDAO();
        List<Producto> productos = dao.obtenerProductosConResenas();

        request.setAttribute("productos", productos);
        request.getRequestDispatcher("moderarResena.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
