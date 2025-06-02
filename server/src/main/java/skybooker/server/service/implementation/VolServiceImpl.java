package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Avion;
import skybooker.server.entity.Vol;
import skybooker.server.entity.Classe;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.repository.AvionRepository;
import skybooker.server.repository.VolRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.VolService;

import java.util.*;

@Service
@Transactional
public class VolServiceImpl implements VolService {

    private final AvionRepository avionRepository;
    private final AeroportRepository aeroportRepository;
    Logger logger = LoggerFactory.getLogger(VolServiceImpl.class);

    private final VolRepository volRepository;
    private final ClasseRepository classeRepository;

    public VolServiceImpl(VolRepository volRepository, ClasseRepository classeRepository, AvionRepository avionRepository, AeroportRepository aeroportRepository) {
        this.volRepository = volRepository;
        this.classeRepository = classeRepository;
        this.avionRepository = avionRepository;
        this.aeroportRepository = aeroportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public VolDTO findById(Long id){
        Optional<Vol> vol = volRepository.findById(id);
        return vol
                .map(VolDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long id){
        volRepository.deleteById(id);
    }

    @Override
    public VolDTO createDTO(VolDTO volDTO) {
        Vol vol = new Vol(volDTO);
        Optional<Avion> avion = avionRepository.findById(volDTO.getAvionId());
        if (avion.isEmpty()) {
            throw new NotFoundException("Avion not found");
        }
        Optional<Aeroport> aeroportArrive = aeroportRepository.findById(volDTO.getAeroportArriveId());
        if (aeroportArrive.isEmpty()) {
            throw new NotFoundException("Aeroport arrive not found");
        }
        Optional<Aeroport> aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId());
        if (aeroportDepart.isEmpty()) {
            throw new NotFoundException("Aeroport depart not found");
        }

        vol.setAvion(avion.get());
        vol.setAeroportArrive(aeroportArrive.get());
        vol.setAeroportDepart(aeroportDepart.get());
        return new VolDTO(volRepository.save(vol));
    }

    @Override
    public VolDTO updateDTO(VolDTO volDTO) {
        Optional<Vol> volOptional = volRepository.findById(volDTO.getId());
        if(volOptional.isPresent()){
            Optional<Avion> avion = avionRepository.findById(volDTO.getAvionId());
            if (avion.isEmpty()) {
                throw new NotFoundException("Avion not found");
            }
            Optional<Aeroport> aeroportArrive = aeroportRepository.findById(volDTO.getAeroportArriveId());
            if (aeroportArrive.isEmpty()) {
                throw new NotFoundException("Aeroport arrive not found");
            }
            Optional<Aeroport> aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId());
            if (aeroportDepart.isEmpty()) {
                throw new NotFoundException("Aeroport depart not found");
            }
            Vol vol = volOptional.get();
            // updating the vol
            vol.setAvion(avion.get());
            vol.setEtat(volDTO.getEtat());
            vol.setAeroportDepart(aeroportDepart.get());
            vol.setAeroportArrive(aeroportArrive.get());
            vol.setPrix(volDTO.getPrix());
            vol.setDateArrive(volDTO.getDateArrive());
            vol.setDateDepart(volDTO.getDateDepart());
            vol.setHeureArrive(volDTO.getHeureArrive());
            vol.setHeureDepart(volDTO.getHeureDepart());

            // saving the update
            return new VolDTO(volRepository.save(vol));
        } else {
            throw new NotFoundException("Vol not found");
        }
    }

    @Override
    public Double calculatePrice(Long volId, Long classeId) {
        //Récupérer le vol et la classe
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new NotFoundException("Vol not found"));

        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new NotFoundException("Classe not found"));

        //Calculer la distance entre aéroports (méthode à implémenter)
        double distance = calculateDistance(
                vol.getAeroportDepart().getLatitude(),
                vol.getAeroportDepart().getLongitude(),
                vol.getAeroportArrive().getLatitude(),
                vol.getAeroportArrive().getLongitude()
        );

        //Calculer le prix final
        return distance * classe.getPrixParKm();
    }

    @Override
    public List<VolDTO> getTrajetVols(Long villeDepartId, Long villeArriveeId) {
        return volRepository.findByVilles(villeDepartId, villeArriveeId)
                .stream().map(VolDTO::new).toList();
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
