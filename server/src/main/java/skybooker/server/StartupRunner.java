package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Client client = new Client();
        client.setEmail("ilyass@gmail.com");
        client.setTelephone("000");
        client.setAdresse("aaa");
        client.setPassword(passwordEncoder.encode("123"));
        clientService.create(client);
    }
}
