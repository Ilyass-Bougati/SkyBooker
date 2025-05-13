package skybooker.server.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skybooker.server.UserDetailsImpl;
import skybooker.server.entity.Client;
import skybooker.server.repository.ClientRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     *
     * @param email the email of the client
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> optionalClient = clientRepository.getByEmail(email);
        if (optionalClient.isPresent()) {
            return new UserDetailsImpl(optionalClient.get());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
