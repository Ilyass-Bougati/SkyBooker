package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Vol;
import skybooker.server.repository.VolRepository;
import skybooker.server.service.AeroportService;
import skybooker.server.service.AvionService;
import skybooker.server.service.VolService;
import java.util.*;

@Service
public class VolServiceImpl implements VolService {

    @Autowired
    public VolRepository volRepository;
    @Autowired
    private AvionService avionService;
    @Autowired
    private AeroportService aeroportService;

    @Override
    public List<Vol> findAll(){
        return volRepository.findAll();
    }

    @Override
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
        Vol oldVol = this.findById(vol.getId());
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
        Vol oldVol = this.findById(volDTO.getId());
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
}
