package skybooker.server.service;

import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Capacite;

import java.util.List;

public interface CapaciteService extends CrudDTO<CapaciteDTO, Long>, CrudService<Capacite, Long> {
    List<CapaciteDTO> findDTOsByAvionId(Long avionId);
}
