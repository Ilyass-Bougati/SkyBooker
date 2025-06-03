package skybooker.server.service;

import skybooker.server.DTO.BilletDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;

import java.util.List;

public interface PassagerService extends CrudDTO<PassagerDTO, Long>, CrudService<Passager, Long> {
    boolean passagerAddedBy(Long clientId, Long passagerId);
    List<BilletDTO> getPassagerBillets(Long passagerId);
}
