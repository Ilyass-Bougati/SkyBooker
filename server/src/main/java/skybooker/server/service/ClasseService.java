package skybooker.server.service;

import skybooker.server.DTO.ClasseDTO;
import skybooker.server.entity.Classe;

import java.util.List;

public interface ClasseService extends CrudDTO<ClasseDTO, Long>, CrudService<Classe, Long> {
    public List<ClasseDTO> findAllDTO();
}
