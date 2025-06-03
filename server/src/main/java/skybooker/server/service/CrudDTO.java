package skybooker.server.service;

import java.util.List;

public interface CrudDTO<DTO, ID> {
    List<DTO> findAllDTO();
    DTO createDTO(DTO dto);
    DTO updateDTO(DTO dto);
    DTO findDTOById(ID id);
    void deleteById(ID id);
}
