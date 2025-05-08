package skybooker.server.service;

import skybooker.server.DTO.BilletDTO;
import skybooker.server.entity.Billet;

public interface BilletService extends CrudService<Billet, Long>, CrudDTO<Billet, BilletDTO> {
}
