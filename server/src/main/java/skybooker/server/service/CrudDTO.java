package skybooker.server.service;

public interface CrudDTO<ENTITY, DTO> {
    ENTITY createDTO(DTO dto);
    ENTITY updateDTO(DTO dto);
}
