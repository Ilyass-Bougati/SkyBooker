package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.VilleDTO;
import skybooker.server.entity.Ville;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.VilleRepository;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VilleServiceImpl implements VilleService {

    private final VilleRepository villeRepository;

    public VilleServiceImpl(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    @Transactional(readOnly = true)
    public List<VilleDTO> getAllVille() {
        List<Ville> villes = villeRepository.findAll();
        return villes.stream().map(VilleDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VilleDTO findDTOById(Long id) {
        Optional<Ville> ville = villeRepository.findById(id);
        return ville
                .map(VilleDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long aLong) {
        villeRepository.deleteById(aLong);
    }

    @Override
    public List<VilleDTO> findAllDTO() {
        return villeRepository.findAll()
                .stream().map(VilleDTO::new).toList();
    }

    @Override
    public VilleDTO createDTO(VilleDTO villeDTO) {
        return new VilleDTO(villeRepository.save(new Ville(villeDTO)));
    }

    @Override
    public VilleDTO updateDTO(VilleDTO villeDTO) {
        Optional<Ville> villeOptional = villeRepository.findById(villeDTO.getId());
        if (villeOptional.isPresent()) {
            Ville ville = villeOptional.get();
            // updating the city
            ville.setNom(villeDTO.getNom());
            ville.setPays(villeDTO.getPays());

            // saving the updates
            return new VilleDTO(villeRepository.save(ville));
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ville> findAll() {
        return villeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Ville findById(Long aLong) {
        Optional<Ville> ville = villeRepository.findById(aLong);
        return ville.orElse(null);
    }

    @Override
    public Ville create(Ville ville) {
        VilleDTO villeDTO = new VilleDTO(ville);
        villeDTO = createDTO(villeDTO);
        return villeRepository.findById(villeDTO.getId())
                .orElseThrow(() -> new NotFoundException("Ville not found"));
    }

    @Override
    public Ville update(Ville ville) {
        VilleDTO villeDTO = new VilleDTO(ville);
        villeDTO = updateDTO(villeDTO);
        return villeRepository.findById(villeDTO.getId())
                .orElseThrow(() -> new NotFoundException("Ville not found"));
    }

    @Override
    public void delete(Ville ville) {
        deleteById(ville.getId());
    }
}
