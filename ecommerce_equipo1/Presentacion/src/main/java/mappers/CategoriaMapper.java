package mappers;

import DTOs.CategoriaDTO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de mapeo (Mapper) que se encarga de la conversión de datos entre la
 * entidad Categoria y su Data Transfer Object (DTO) correspondiente.
 *
 * @author Alicia
 */
public class CategoriaMapper {

    /**
     * Convierte un objeto de entidad Categoria a un objeto CategoriaDTO.
     *
     * @param categoria La entidad Categoria de origen.
     * @return Un nuevo objeto CategoriaDTO con los datos mapeados.
     */
    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setActiva(categoria.isActiva());
        return categoriaDTO;
    }

    /**
     * Convierte una lista de entidades Categoria a una lista de CategoriaDTO.
     *
     * @param categorias La lista de entidades Categoria de origen.
     * @return Una lista de objetos CategoriaDTO.
     */
    public static List<CategoriaDTO> toDTOList(List<Categoria> categorias) {
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasDTO.add(toDTO(categoria));
        }
        return categoriasDTO;
    }

    /**
     * Convierte un objeto CategoriaDTO a un objeto de entidad Categoria.
     *
     * @param categoriaDTO El DTO de Categoria de origen.
     * @return Un nuevo objeto Categoria con los datos mapeados.
     */
    public static Categoria toEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setActiva(categoriaDTO.isActiva());
        return categoria;
    }
}
