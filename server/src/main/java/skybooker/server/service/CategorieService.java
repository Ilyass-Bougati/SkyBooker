package skybooker.server.service;

import skybooker.server.DTO.CategorieDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.enums.CategorieNameEnum;

import java.util.List;

public interface CategorieService extends CrudDTO<CategorieDTO, Long>, CrudService<Categorie, Long> {
    CategorieDTO findByNom(CategorieNameEnum name);
    List<CategorieDTO> getAllCategories();
}
