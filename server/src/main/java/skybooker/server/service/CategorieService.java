package skybooker.server.service;

import skybooker.server.DTO.CategorieDTO;
import skybooker.server.entity.Categorie;

import java.util.List;

public interface CategorieService extends CrudDTO<CategorieDTO, Long>, CrudService<Categorie, Long> {
    CategorieDTO findByNom(String name);
    List<CategorieDTO> getAllCategories();
}
