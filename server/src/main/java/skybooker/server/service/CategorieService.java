package skybooker.server.service;

import skybooker.server.DTO.CategorieDTO;
import skybooker.server.enums.CategorieNameEnum;

import java.util.List;

public interface CategorieService extends CrudDTO<CategorieDTO, Long> {
    CategorieDTO findByNom(CategorieNameEnum name);
    List<CategorieDTO> getAllCategories();
}
