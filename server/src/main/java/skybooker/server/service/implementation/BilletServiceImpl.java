package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Classe;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.BilletRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.service.BilletService;

import java.util.Optional;

@Service
@Transactional
public class BilletServiceImpl implements BilletService {

    private final BilletRepository billetRepository;
    private final ClasseRepository classeRepository;
    private final PassagerRepository passagerRepository;
    private final ReservationRepository reservationRepository;

    public BilletServiceImpl(BilletRepository billetRepository, ClasseRepository classeRepository, PassagerRepository passagerRepository, ReservationRepository reservationRepository) {
        this.billetRepository = billetRepository;
        this.classeRepository = classeRepository;
        this.passagerRepository = passagerRepository;
        this.reservationRepository = reservationRepository;
    }



    @Override
    @Transactional(readOnly = true)
    public BilletDTO findById(Long id) {
        Optional<Billet> billet = billetRepository.findById(id);
        return billet
                .map(BilletDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        billetRepository.deleteById(id);
    }

    @Override
    public BilletDTO createDTO(BilletDTO billetDTO) {
        Classe classe = classeRepository.findById(billetDTO.getClasseId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));
        Passager passager = passagerRepository.findById(billetDTO.getPassagerId())
                .orElseThrow(() -> new NotFoundException("Passager not found"));
        Reservation reservation = reservationRepository.findById(billetDTO.getReservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        Billet billet = new Billet();
        billet.setSiege(billetDTO.getSiege());
        billet.setClasse(classe);
        billet.setPassager(passager);
        billet.setReservation(reservation);
        return new BilletDTO(billetRepository.save(billet));
    }

    @Override
    public BilletDTO updateDTO(BilletDTO billetDTO) {
        Billet billet = billetRepository.findById(billetDTO.getId())
                .orElseThrow(() -> new NotFoundException("Billet not found"));
        Classe classe = classeRepository.findById(billetDTO.getClasseId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));
        Passager passager = passagerRepository.findById(billetDTO.getPassagerId())
                .orElseThrow(() -> new NotFoundException("Passager not found"));
        Reservation reservation = reservationRepository.findById(billetDTO.getReservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        // updating the billet
        billet.setSiege(billetDTO.getSiege());
        billet.setClasse(classe);
        billet.setReservation(reservation);
        billet.setPassager(passager);

        // saving the updates
        return new BilletDTO(billetRepository.save(billet));
    }
}
