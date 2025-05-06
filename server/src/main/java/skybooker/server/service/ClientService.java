package skybooker.server.service;

import skybooker.server.DTO.ClientDTO;
import skybooker.server.entity.Client;

public interface ClientService extends CrudService<Client, Long>{
    void deleteByEmail(String email);
    Client findByEmail(String email);
    Client update(Client client, ClientDTO clientDTO);
}
