/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTOs.CategoriaDTO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alici
 */
public class CategoriaMapper {

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setActiva(categoria.isActiva());
        return categoriaDTO;
    }

    public static List<CategoriaDTO> toDTOList(List<Categoria> categorias) {
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasDTO.add(toDTO(categoria));
        }
        return categoriasDTO;
    }

    public static Categoria toEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setActiva(categoriaDTO.isActiva());
        return categoria;
    }
}
