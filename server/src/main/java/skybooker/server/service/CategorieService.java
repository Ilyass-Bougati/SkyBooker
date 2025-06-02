package skybooker.server.service;

import skybooker.server.DTO.CategorieDTO;

import java.util.List;

public interface CategorieService extends CrudDTO<CategorieDTO, Long> {
    CategorieDTO findByNom(String name);
    List<CategorieDTO> getAllCategories();
}
