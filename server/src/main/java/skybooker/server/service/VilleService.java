package skybooker.server.service;

import skybooker.server.DTO.VilleDTO;
import skybooker.server.entity.Ville;

import java.util.List;

public interface VilleService extends CrudDTO<VilleDTO, Long>, CrudService<Ville, Long> {
    public List<VilleDTO> getAllVille();
}
