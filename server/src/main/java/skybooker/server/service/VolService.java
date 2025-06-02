package skybooker.server.service;

import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;

import java.util.List;


public interface VolService extends CrudDTO<VolDTO, Long> {
    Double calculatePrice(Long volId, Long classeId);
    List<Vol> getTrajetVols(Long villeDepartId, Long villeArriveeId);
}