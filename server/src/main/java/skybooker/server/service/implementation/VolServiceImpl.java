package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;
import skybooker.server.entity.Classe;
import skybooker.server.repository.VolRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.AeroportService;
import skybooker.server.service.AvionService;
import skybooker.server.service.VolService;
import java.util.*;

@Service
@Transactional
public class VolServiceImpl implements VolService {

    Logger logger = LoggerFactory.getLogger(VolServiceImpl.class);

    private final VolRepository volRepository;
    private final ClasseRepository classeRepository;
    private final AvionService avionService;
    private final AeroportService aeroportService;

    public VolServiceImpl(VolRepository volRepository, ClasseRepository classeRepository, AvionService avionService, AeroportService aeroportService) {
        this.volRepository = volRepository;
        this.classeRepository = classeRepository;
        this.avionService = avionService;
        this.aeroportService = aeroportService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vol> findAll(){
        return volRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vol findById(Long id){
        Optional<Vol> vol = volRepository.findById(id);
        return vol.orElse(null);
    }

    @Override
    public Vol create(Vol vol){
        return volRepository.save(vol);
    }

    @Override
    public Vol update(Vol vol){
        Vol oldVol = findById(vol.getId());
        if(oldVol != null){
            return volRepository.save(oldVol);
        }else{
            return null;
        }
    }

    @Override
    public void deleteById(Long id){
        volRepository.deleteById(id);
    }

    @Override
    public void delete(Vol vol){
        volRepository.delete(vol);
    }

    @Override
    public Vol createDTO(VolDTO volDTO) {
        Vol vol = new Vol(volDTO);
        vol.setAvion(avionService.findById(volDTO.getAvionId()));
        vol.setAeroportArrive(aeroportService.findById(volDTO.getAeroportArriveId()));
        vol.setAeroportDepart(aeroportService.findById(volDTO.getAeroportDepartId()));
        return volRepository.save(vol);
    }

    @Override
    public Vol updateDTO(VolDTO volDTO) {
        Vol oldVol = findById(volDTO.getId());
        if(oldVol != null){
            // updating the vol
            oldVol.setAvion(avionService.findById(volDTO.getAvionId()));
            oldVol.setEtat(volDTO.getEtat());
            oldVol.setAeroportDepart(aeroportService.findById(volDTO.getAeroportDepartId()));
            oldVol.setAeroportArrive(aeroportService.findById(volDTO.getAeroportArriveId()));
            oldVol.setPrix(volDTO.getPrix());
            oldVol.setDateArrive(volDTO.getDateArrive());
            oldVol.setDateDepart(volDTO.getDateDepart());
            oldVol.setHeureArrive(volDTO.getHeureArrive());
            oldVol.setHeureDepart(volDTO.getHeureDepart());

            // saving the update
            return volRepository.save(oldVol);
        } else {
            return null;
        }
    }

    @Override
    public Double calculatePrice(Long volId, Long classeId) {
        //Récupérer le vol et la classe
        Vol vol = findById(volId);
        Classe classe = classeRepository.findById(classeId).orElse(null);

        if (vol == null || classe == null) {
            return null;
        }

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

    //(formule Haversine)
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
