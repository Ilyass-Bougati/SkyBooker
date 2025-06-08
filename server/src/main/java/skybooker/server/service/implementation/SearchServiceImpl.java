package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import skybooker.server.DTO.SearchDTO;
import skybooker.server.entity.Client;
import skybooker.server.entity.Search;
import skybooker.server.entity.Ville;
import skybooker.server.repository.SearchRepository;
import skybooker.server.repository.VilleRepository;
import skybooker.server.service.SearchService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final VilleRepository villeRepository;

    public SearchServiceImpl(SearchRepository searchRepository, VilleRepository villeRepository) {
        this.searchRepository = searchRepository;
        this.villeRepository = villeRepository;
    }

    @Override
    public void addSearch(Long villeDepartId, Long villeArriveId, Client client) {
        Ville villeDepart = villeRepository.findById(villeDepartId)
                .orElseThrow(() -> new RuntimeException("Ville depart not found"));

        Ville villeArrive = villeRepository.findById(villeArriveId)
                .orElseThrow(() -> new RuntimeException("Ville arrive not found"));

        Search search = new Search();
        search.setVilleDepart(villeDepart);
        search.setVilleArrivee(villeArrive);
        search.setClient(client);
        searchRepository.save(search);
    }

    @Override
    public List<SearchDTO> getHistory(Long clientId) {
        return searchRepository.getHistory(clientId)
                .stream().map(SearchDTO::new).toList();
    }
}
