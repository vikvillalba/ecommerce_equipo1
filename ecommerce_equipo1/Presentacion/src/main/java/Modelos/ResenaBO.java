package Modelos;

import DAOs.ResenaDAO;
import entidades.Producto;
import entidades.Resena;
import java.util.List;

/**
 *
 * @author victoria
 */
public class ResenaBO {

    private final ResenaDAO resenaDAO;

    public ResenaBO() {
        this.resenaDAO = new ResenaDAO();
    }

    public List<Resena> obtenerTodasLasResenas() {
        return resenaDAO.obtenerTodasLasResenas();
    }

    public void eliminarResena(Integer id) {
        if (id != null) {
            resenaDAO.eliminarResena(id);
        }
    }

    public void moderarResena(Integer id) {
        if (id != null) {
            String mensajeModeracion = "Este comentario ha sido ocultado por el administrador por infringir las normas de la comunidad.";
            
            resenaDAO.moderarResena(id, mensajeModeracion);
        }
    }
}
