package skybooker.server.service;

import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;


public interface VolService extends CrudService<Vol, Long>, CrudDTO<Vol, VolDTO> {
    Double calculatePrice(Long volId, Long classeId);
}