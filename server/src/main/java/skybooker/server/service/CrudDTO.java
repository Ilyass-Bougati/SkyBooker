package skybooker.server.service;

public interface CrudDTO<DTO, ID> {
    DTO createDTO(DTO dto);
    DTO updateDTO(DTO dto);
    DTO findById(ID id);
    void deleteById(ID id);
}
