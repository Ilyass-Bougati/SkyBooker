package skybooker.server.service;

import java.util.List;

public interface CrudDTO<DTO, ID> {
    List<DTO> findAll();
    DTO createDTO(DTO dto);
    DTO updateDTO(DTO dto);
    DTO findById(ID id);
    void deleteById(ID id);
}
