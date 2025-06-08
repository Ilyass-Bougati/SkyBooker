package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value = "volCache", key = "#id")
    public VolDTO findDTOById(Long id){
        Optional<Vol> vol = volRepository.findById(id);
        return vol
                .map(VolDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @CacheEvict(value = "volCache", key = "#id")
    public void deleteById(Long id){
        volRepository.deleteById(id);
    }

    @Override
    public List<VolDTO> findAllDTO() {
        return volRepository.findAll()
                .stream().map(VolDTO::new).toList();
    }

    @Override
    public VolDTO createDTO(VolDTO volDTO) {
        Vol vol = new Vol(volDTO);
        Avion avion = avionRepository.findById(volDTO.getAvionId())
                .orElseThrow(() -> new NotFoundException("Avion not found"));
        Aeroport aeroportArrive = aeroportRepository.findById(volDTO.getAeroportArriveId())
                .orElseThrow(() -> new NotFoundException("Aeroport arrive not found"));
        Aeroport aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId())
                .orElseThrow(() -> new NotFoundException("Aeroport depart not found"));

        vol.setAvion(avion);
        vol.setAeroportArrive(aeroportArrive);
        vol.setAeroportDepart(aeroportDepart);
        return new VolDTO(volRepository.save(vol));
    }

    @Override
    @Caching(put = {
            @CachePut(value = "volCache", key = "#volDTO.id")
    }, evict = {
            @CacheEvict(value = "trajetVolsCache", allEntries = true)
    })
    public VolDTO updateDTO(VolDTO volDTO) {
        Optional<Vol> volOptional = volRepository.findById(volDTO.getId());
        if(volOptional.isPresent()){
            Avion avion = avionRepository.findById(volDTO.getAvionId())
                    .orElseThrow(() -> new NotFoundException("Avion not found"));
            Aeroport aeroportArrive = aeroportRepository.findById(volDTO.getAeroportArriveId())
                    .orElseThrow(() -> new NotFoundException("Aeroport arrive not found"));
            Aeroport aeroportDepart = aeroportRepository.findById(volDTO.getAeroportDepartId())
                    .orElseThrow(() -> new NotFoundException("Aeroport depart not found"));

            Vol vol = volOptional.get();
            // updating the vol
            vol.setAvion(avion);
            vol.setEtat(volDTO.getEtat());
            vol.setAeroportDepart(aeroportDepart);
            vol.setAeroportArrive(aeroportArrive);
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
    @Cacheable(value = "trajetVolsCache", key = "{#villeDepartId, #villeArriveeId}")
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


    @Override
    @Transactional(readOnly = true)
    public List<Vol> findAll() {
        return volRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vol findById(Long id) {
        Optional<Vol> vol = volRepository.findById(id);
        return vol.orElse(null);
    }

    @Override
    public Vol create(Vol vol) {
        VolDTO volDTO = new VolDTO(vol);
        volDTO = createDTO(volDTO);
        return volRepository.findById(volDTO.getId())
                .orElseThrow(() -> new NotFoundException("Vol not found"));
    }

    @Override
    public Vol update(Vol vol) {
        VolDTO volDTO = new VolDTO(vol);
        volDTO = updateDTO(volDTO);
        return volRepository.findById(volDTO.getId())
                .orElseThrow(() -> new NotFoundException("Vol not found"));
    }

    @Override
    public void delete(Vol vol) {
        deleteById(vol.getId());
    }
}
