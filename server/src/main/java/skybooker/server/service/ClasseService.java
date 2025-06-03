package skybooker.server.service;

import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.entity.Classe;

import java.util.List;
import java.util.Set;

public interface ClasseService extends CrudDTO<ClasseDTO, Long>, CrudService<Classe, Long> {
    List<ClasseDTO> findAllDTO();

    List<ClasseDTO> findDTOsByIds(Set<Long> ids);

}
