package Controladores;

import DTOs.ProductoDTO;
import Modelos.ProductoBO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

/**
 *
 * @author victoria
 */
@WebServlet(name = "CategoriaProductoServlet", urlPatterns = {"/admin/adminProductos"})
@MultipartConfig
public class AdminProductosServlet extends HttpServlet {

    private ProductoBO bo = new ProductoBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ProductoDTO> productos = bo.obtenerProductos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("/admin/adminProductos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al cargar productos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "agregar":
                    crearProducto(request);
                    break;
                case "editar":
                    actualizarProducto(request);
                    break;
                case "eliminar":
                    eliminarProducto(request);
                    break;
                default:
                    System.out.println("Acción no reconocida");
            }
            response.sendRedirect(request.getContextPath() + "/admin/adminProductos");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al procesar la acción: " + e.getMessage());
        }
    }

    private void crearProducto(HttpServletRequest req) throws Exception {
        ProductoDTO p = new ProductoDTO();
        p.setNombre(req.getParameter("nombre"));
        p.setDescripcion(req.getParameter("descripcion"));
        p.setPrecio(Double.parseDouble(req.getParameter("precio")));
        p.setExistencias(Integer.parseInt(req.getParameter("stock")));
        p.setColorHex(req.getParameter("color"));
        p.setEspecificaciones(req.getParameter("especificaciones"));

        String idCatStr = req.getParameter("idCategoria");
        if (idCatStr != null && !idCatStr.isEmpty()) {
            p.setCategoriaId(Long.valueOf(idCatStr));
        }

        Part filePart = req.getPart("imagen");
        String rutaImagen = guardarImagen(filePart, req);

        if (rutaImagen == null) {
            rutaImagen = "img/placeholder.jpg";
        }

        p.setImagen(rutaImagen);

        bo.agregarProducto(p);
    }

    private void actualizarProducto(HttpServletRequest req) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));

        ProductoDTO p = bo.obtenerPorId(id);

        if (p != null) {
            p.setNombre(req.getParameter("nombre"));
            p.setDescripcion(req.getParameter("descripcion"));
            p.setPrecio(Double.parseDouble(req.getParameter("precio")));
            p.setExistencias(Integer.parseInt(req.getParameter("stock")));
            p.setColorHex(req.getParameter("color"));
            p.setEspecificaciones(req.getParameter("especificaciones"));

            String idCatStr = req.getParameter("idCategoria");
            if (idCatStr != null && !idCatStr.isEmpty()) {
                p.setCategoriaId(Long.parseLong(idCatStr));
            }

            Part filePart = req.getPart("imagen");
            String nuevaRuta = guardarImagen(filePart, req);

            if (nuevaRuta != null) {
                p.setImagen(nuevaRuta);
            }

            bo.actualizarProducto(p);
        }
    }

    private void eliminarProducto(HttpServletRequest req) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        ProductoDTO p = new ProductoDTO();
        p.setId(id);
        bo.eliminarProducto(p);
    }

    private String guardarImagen(Part filePart, HttpServletRequest req) {
        try {
            if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName().isEmpty()) {
                return null;
            }

            String uploadPath = req.getServletContext().getRealPath("") + File.separator + "img";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String fileName = UUID.randomUUID().toString() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            filePart.write(uploadPath + File.separator + fileName);

            return "img/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
