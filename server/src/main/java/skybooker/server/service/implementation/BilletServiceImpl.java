package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.entity.Billet;
import skybooker.server.repository.BilletRepository;
import skybooker.server.service.BilletService;
import skybooker.server.service.ClasseService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.ReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class BilletServiceImpl implements BilletService {

    private final BilletRepository billetRepository;
    private final ClasseService classeService;
    private final PassagerService passagerService;

    public BilletServiceImpl(BilletRepository billetRepository, ClasseService classeService, PassagerService passagerService, ReservationService reservationService) {
        this.billetRepository = billetRepository;
        this.classeService = classeService;
        this.passagerService = passagerService;
        this.reservationService = reservationService;
    }

    private final ReservationService reservationService;

    @Override
    @Transactional(readOnly = true)
    public List<Billet> findAll() {
        return billetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Billet findById(Long id) {
        Optional<Billet> billet = billetRepository.findById(id);
        return billet.orElse(null);
    }

    @Override
    @Transactional
    public Billet create(Billet billet) {
        return billetRepository.save(billet);
    }

    @Override
    @Transactional
    public Billet update(Billet billet) {
        return billetRepository.save(billet);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        billetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Billet billet) {
        billetRepository.delete(billet);
    }

    @Override
    @Transactional
    public Billet createDTO(BilletDTO billetDTO) {
        Billet billet = new Billet();
        billet.setSiege(billetDTO.getSiege());
        billet.setClasse(classeService.findById(billetDTO.getClasseId()));
        billet.setPassager(passagerService.findById(billetDTO.getPassagerId()));
        billet.setReservation(reservationService.findById(billetDTO.getReservationId()));
        return billetRepository.save(billet);
    }

    @Override
    @Transactional
    public Billet updateDTO(BilletDTO billetDTO) {
        Billet billet = findById(billetDTO.getId());
        if (billet != null) {
            // updating the billet
            billet.setSiege(billetDTO.getSiege());
            billet.setClasse(classeService.findById(billetDTO.getClasseId()));
            billet.setReservation(reservationService.findById(billetDTO.getReservationId()));
            billet.setPassager(passagerService.findById(billetDTO.getPassagerId()));

            // saving the updates
            return billetRepository.save(billet);
        } else {
            return null;
        }
    }
}
