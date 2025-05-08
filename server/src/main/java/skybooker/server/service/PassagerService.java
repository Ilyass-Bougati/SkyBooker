package skybooker.server.service;

import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;

public interface PassagerService extends CrudService<Passager, Long>, CrudDTO<Passager, PassagerDTO> {
}
