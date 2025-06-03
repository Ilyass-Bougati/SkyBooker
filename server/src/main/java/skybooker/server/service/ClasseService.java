package skybooker.server.service;

import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.entity.Classe;

import java.util.List;
import java.util.Set;

public interface ClasseService extends CrudDTO<ClasseDTO, Long>, CrudService<Classe, Long> {
    public List<ClasseDTO> findAllDTO();

    @Transactional(readOnly = true)
    List<ClasseDTO> findDTOsByIds(Set<Long> ids);

}
