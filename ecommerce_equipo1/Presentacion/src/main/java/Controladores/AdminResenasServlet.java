package Controladores;

import DAOs.ProductoDAO;
import DAOs.ResenaDAO;
import Modelos.ResenaBO;
import entidades.Producto;
import entidades.Resena;
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

    private final ResenaBO resenaBO = new ResenaBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Resena> listaResenas = resenaBO.obtenerTodasLasResenas();

        request.setAttribute("resenas", listaResenas);

        request.getRequestDispatcher("moderarResena.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idResena");
        String accion = request.getParameter("accion");

        if (idStr != null && accion != null) {
            int id = Integer.parseInt(idStr);

            if (accion.equals("eliminar")) {
                resenaBO.eliminarResena(id);
            } else if (accion.equals("moderar")) {
                resenaBO.moderarResena(id);
            }
        }
        response.sendRedirect("adminResenas");
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
