package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<Billet> findAll() {
        return billetRepository.findAll();
    }

    @Override
    public Billet findById(Long aLong) {
        Optional<Billet> billet = billetRepository.findById(aLong);
        return billet.orElse(null);
    }

    @Override
    public Billet create(Billet entity) {
        return billetRepository.save(entity);
    }

    @Override
    public Billet update(Billet entity) {
        return billetRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        billetRepository.deleteById(aLong);
    }

    @Override
    public void delete(Billet entity) {
        billetRepository.delete(entity);
    }

    @Override
    public Billet createDTO(BilletDTO billetDTO) {
        Billet billet = new Billet();
        // TODO : change this to implement the siege logic
        billet.setSiege(1);
        billet.setClasse(classeService.findById(billetDTO.getClasseId()));
        billet.setPassager(passagerService.findById(billetDTO.getPassagerId()));
        billet.setReservation(reservationService.findById(billetDTO.getReservationId()));
        return billetRepository.save(billet);
    }

    @Override
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
