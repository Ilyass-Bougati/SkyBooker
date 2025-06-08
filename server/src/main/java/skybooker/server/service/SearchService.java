package skybooker.server.service;

import skybooker.server.DTO.SearchDTO;
import skybooker.server.entity.Client;

import java.util.List;

public interface SearchService {
    void addSearch(Long villeDepartId, Long villeArriveId, Client client);
    List<SearchDTO> getHistory(Long clientId);
}
