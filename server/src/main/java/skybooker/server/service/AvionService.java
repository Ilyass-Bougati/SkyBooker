package skybooker.server.service;

import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;

public interface AvionService extends CrudService<Avion, Long>, CrudDTO<Avion, AvionDTO> {
}
