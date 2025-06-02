package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Classe;
import skybooker.server.entity.Client;
import skybooker.server.entity.Role;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.RoleRepository;
import skybooker.server.service.ClientService;

import javax.sql.DataSource;

import static skybooker.server.enums.CategoryNameEnum.*;

@Component
public class StartupRunner implements CommandLineRunner {

    private final CategorieRepository categorieRepository;
    private final ClasseRepository classeRepository;
    private final ClientService clientService;
    private final RoleRepository roleRepository;
    private final DataSource dataSource;

    public StartupRunner(RoleRepository roleRepository, ClientService clientService, CategorieRepository categorieRepository, ClasseRepository classeRepository, DataSource dataSource) {
        this.roleRepository = roleRepository;
        this.clientService = clientService;
        this.categorieRepository = categorieRepository;
        this.classeRepository = classeRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating the default categories
        Categorie standard = new Categorie();
        standard.setNom(STANDARD);
        standard.setReduction(0);

        Categorie junior = new Categorie();
        junior.setNom(JUNIOR);
        junior.setReduction(0.4);

        Categorie senior = new Categorie();
        senior.setNom(SENIOR);
        senior.setReduction(0.25);

        categorieRepository.save(standard);
        categorieRepository.save(junior);
        categorieRepository.save(senior);

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
        role.setAuthority("ROLE_USER");

        Role role2 = new Role();
        role2.setAuthority("ROLE_ADMIN");

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

        Client client1 = new Client();
        client1.setEmail("abde@gmail.com");
        client1.setTelephone("000");
        client1.setAdresse("aaa");
        client1.setPassword("123");
        client1.setRole(role2);
        clientService.create(client1);

        Client amine = new Client();
        amine.setEmail("amine@gmail.com");
        amine.setTelephone("000");
        amine.setAdresse("aaa");
        amine.setPassword("123");
        amine.setRole(role);
        clientService.create(amine);

        Client another = new Client();
        another.setEmail("another@gmail.com");
        another.setTelephone("000");
        another.setAdresse("aaa");
        another.setPassword("123");
        another.setRole(role);
        clientService.create(another);

        executeSqlScript();
    }

    private void executeSqlScript() {
        try {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource("data/mock_data.sql"));
            resourceDatabasePopulator.execute(dataSource);
            System.out.println("SQL script for mock data executed successfully");
        } catch (Exception e) {
            System.err.println("Failed to execute SQL script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
