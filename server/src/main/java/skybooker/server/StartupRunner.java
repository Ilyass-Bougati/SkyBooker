package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Classe;
import skybooker.server.entity.Client;
import skybooker.server.entity.Role;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.RoleRepository;
import skybooker.server.service.ClientService;

@Component
public class StartupRunner implements CommandLineRunner {

    private final CategorieRepository categorieRepository;
    private final ClasseRepository classeRepository;
    private final ClientService clientService;
    private final RoleRepository roleRepository;

    public StartupRunner(RoleRepository roleRepository, ClientService clientService, CategorieRepository categorieRepository, ClasseRepository classeRepository) {
        this.roleRepository = roleRepository;
        this.clientService = clientService;
        this.categorieRepository = categorieRepository;
        this.classeRepository = classeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating the default categories
        Categorie standard = new Categorie();
        standard.setNom("Standard");
        standard.setReduction(0);

        Categorie junior = new Categorie();
        junior.setNom("Junior");
        junior.setReduction(0.4);

        Categorie senior = new Categorie();
        senior.setNom("Senior");
        senior.setReduction(0.25);

        Categorie student = new Categorie();
        student.setNom("Student");
        student.setReduction(0.15);

        categorieRepository.save(standard);
        categorieRepository.save(junior);
        categorieRepository.save(senior);
        categorieRepository.save(student);

        // Creating the default classes
        Classe economy = new Classe();
        economy.setNom("Economy");
        economy.setPrixParKm(10);

        Classe business = new Classe();
        business.setNom("Business");
        business.setPrixParKm(30);

        Classe first_class = new Classe();
        first_class.setNom("First Class");
        first_class.setPrixParKm(60);

        classeRepository.save(economy);
        classeRepository.save(business);
        classeRepository.save(first_class);

        // Create the roles
        Role role = new Role();
        role.setNom("ROLE_USER");

        Role role2 = new Role();
        role2.setNom("ROLE_ADMIN");

        roleRepository.save(role);
        roleRepository.save(role2);

        // Creating a default user
        Client client = new Client();
        client.setEmail("ilyass@gmail.com");
        client.setTelephone("000");
        client.setAdresse("aaa");
        client.setPassword("123");
        client.setRole(role2);
        clientService.create(client);
    }
}
