package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Vol;
import skybooker.server.repository.VolRepository;
import skybooker.server.service.VolService;
import java.util.*;

@Service
public class VolServiceImpl implements VolService {

    @Autowired
    public VolRepository volRepository;

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

}
