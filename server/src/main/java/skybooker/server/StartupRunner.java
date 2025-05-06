package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Client;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.service.ClientService;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public void run(String... args) throws Exception {
        Client client = new Client();
        client.setEmail("ilyass@gmail.com");
        client.setTelephone("000");
        client.setAdresse("aaa");
        client.setPassword("123");
        clientService.create(client);

        // Creating the default categories
        Categorie normale = new Categorie();
        normale.setNom("normale");
        normale.setReduction(0);

        Categorie senior = new Categorie();
        senior.setNom("senior");
        senior.setReduction(0.2);

        Categorie enfant = new Categorie();
        enfant.setNom("enfant");
        enfant.setReduction(0.25);

        categorieRepository.save(normale);
        categorieRepository.save(senior);
        categorieRepository.save(enfant);
    }
}
