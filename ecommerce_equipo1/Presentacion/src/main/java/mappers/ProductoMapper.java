package mappers;

import DTOs.ProductoDTO;
import entidades.Categoria;
import entidades.Producto;

/**
 *
 * @author victoria
 */
public class ProductoMapper {
// Convertir de Entidad (BD) a DTO (Vista)

    public static ProductoDTO toDTO(Producto entity) {
        if (entity == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEspecificaciones(entity.getEspecificaciones());
        dto.setPrecio(entity.getPrecio());
        dto.setExistencias(entity.getExistencias());
        dto.setImagen(entity.getImagen());
        dto.setColorHex(entity.getColorHex());
        dto.setTalla(entity.getTalla());
        dto.setDisponibilidad(entity.isDisponibilidad());

        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
            dto.setCategoriaNombre(entity.getCategoria().getNombre());
        }

        return dto;
    }

    // Convertir de DTO (Vista) a Entidad (BD)
    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto entity = new Producto();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEspecificaciones(dto.getEspecificaciones());
        entity.setPrecio(dto.getPrecio());
        entity.setExistencias(dto.getExistencias());
        entity.setImagen(dto.getImagen());
        entity.setColorHex(dto.getColorHex());
        entity.setTalla(dto.getTalla());
        entity.setDisponibilidad(dto.isDisponibilidad());

        if (dto.getCategoriaId() != null) {
            Categoria cat = new Categoria();
            cat.setId(dto.getCategoriaId());
            entity.setCategoria(cat);
        }

        return entity;
    }
}
