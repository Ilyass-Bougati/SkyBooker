package skybooker.server.service;

import skybooker.server.DTO.CategorieDTO;
import skybooker.server.enums.CategoryNameEnum;

import java.util.List;

public interface CategorieService extends CrudDTO<CategorieDTO, Long> {
    CategorieDTO findByNom(CategoryNameEnum name);
    List<CategorieDTO> getAllCategories();
}
