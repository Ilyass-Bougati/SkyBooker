package skybooker.server.service;

import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;

public interface AvionService extends CrudDTO<AvionDTO, Long>, CrudService<Avion, Long> {
}
