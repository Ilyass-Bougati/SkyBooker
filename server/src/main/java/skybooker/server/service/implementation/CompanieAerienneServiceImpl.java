package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.CompanieAerienneRepository;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanieAerienneServiceImpl implements CompanieAerienneService {

    private final CompanieAerienneRepository companieAerienneRepository;

    public CompanieAerienneServiceImpl(CompanieAerienneRepository companieAerienneRepository) {
        this.companieAerienneRepository = companieAerienneRepository;
    }

    @Override
    public List<CompanieAerienne> findAll() {
        return companieAerienneRepository.findAll();
    }

    @Override
    public CompanieAerienne findById(Long aLong) {
        Optional<CompanieAerienne> companieAerienne = companieAerienneRepository.findById(aLong);
        return companieAerienne.orElse(null);
    }

    @Override
    public CompanieAerienne create(CompanieAerienne entity) {
        return companieAerienneRepository.save(entity);
    }

    @Override
    public CompanieAerienne update(CompanieAerienne entity) {
        return companieAerienneRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        companieAerienneRepository.deleteById(aLong);
    }

    @Override
    public void delete(CompanieAerienne entity) {
        companieAerienneRepository.delete(entity);
    }

    @Override
    public CompanieAerienne createDTO(CompanieAerienneDTO companieAerienneDTO) {
        return create(new CompanieAerienne(companieAerienneDTO));
    }

    @Override
    public CompanieAerienne updateDTO(CompanieAerienneDTO companieAerienneDTO) {
        CompanieAerienne companieAerienne = findById(companieAerienneDTO.getId());
        if (companieAerienne != null) {
            // updating the airline
            companieAerienne.setNom(companieAerienneDTO.getNom());
            companieAerienne.setIcaoCode(companieAerienneDTO.getIcaoCode());
            companieAerienne.setIataCode(companieAerienneDTO.getIataCode());

            // saving the updated airline
            return companieAerienneRepository.save(companieAerienne);
        } else {
            return null;
        }
    }
}
