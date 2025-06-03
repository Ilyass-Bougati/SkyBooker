package skybooker.server.service;

import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;

import java.util.List;
import java.util.Set;

public interface PassagerService extends CrudDTO<PassagerDTO, Long>, CrudService<Passager, Long> {
    List<PassagerDTO> findDTOsByIds(Set<Long> ids);

    boolean passagerAddedBy(Long clientId, Long passagerId);
    List<BilletDTO> getPassagerBillets(Long passagerId);
}
