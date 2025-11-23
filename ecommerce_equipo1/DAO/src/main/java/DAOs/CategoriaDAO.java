/*
 * 
 */
package DAOs;

import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack Murrieta
 */
public class CategoriaDAO {

    private static List<Categoria> categorias = new ArrayList<>();
    private static int contadorId = 1;

    static {
        // categorias existentes del enum
        categorias.add(new Categoria(contadorId++, "PANTALONES", true));
        categorias.add(new Categoria(contadorId++, "FALDAS", true));
        categorias.add(new Categoria(contadorId++, "SHORTS", true));
        categorias.add(new Categoria(contadorId++, "BLUSAS", true));
        categorias.add(new Categoria(contadorId++, "CAMISAS", true));
        categorias.add(new Categoria(contadorId++, "VESTIDOS", true));
        categorias.add(new Categoria(contadorId++, "ZAPATOS", true));
        categorias.add(new Categoria(contadorId++, "CHAMARRAS", true));
        categorias.add(new Categoria(contadorId++, "ACCESORIOS", true));
    }

    public List<Categoria> listar() {
        return new ArrayList<>(categorias);
    }

    public List<Categoria> listarActivas() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (c.isActiva()) {
                activas.add(c);
            }
        }
        return activas;
    }

    public Categoria obtenerPorId(Integer id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Categoria obtenerPorNombre(String nombre) {
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public boolean agregar(Categoria categoria) {
        try {
            categoria.setId(contadorId++);
            categoria.setActiva(true);
            categorias.add(categoria);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Categoria categoria) {
        Categoria existente = obtenerPorId(categoria.getId());
        if (existente != null) {
            existente.setNombre(categoria.getNombre());
            return true;
        }
        return false;
    }

    public boolean eliminar(Integer id) {
        Categoria categoria = obtenerPorId(id);
        if (categoria != null) {
            categorias.remove(categoria);
            return true;
        }
        return false;
    }

    public boolean activar(Integer id) {
        Categoria categoria = obtenerPorId(id);
        if (categoria != null) {
            categoria.setActiva(true);
            return true;
        }
        return false;
    }

    public boolean desactivar(Integer id) {
        Categoria categoria = obtenerPorId(id);
        if (categoria != null) {
            categoria.setActiva(false);
            return true;
        }
        return false;
    }
}
