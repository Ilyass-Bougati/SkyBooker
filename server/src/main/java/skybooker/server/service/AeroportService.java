package skybooker.server.service;

import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;

public interface AeroportService extends CrudService<Aeroport, Long> {
    Aeroport create(AeroportDTO entity);
    Aeroport update(AeroportDTO entity);
}
