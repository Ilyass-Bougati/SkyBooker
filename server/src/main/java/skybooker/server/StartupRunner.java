package skybooker.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Classe;
import skybooker.server.entity.Role;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.RoleRepository;
import skybooker.server.service.ClientService;

import javax.sql.DataSource;

import static skybooker.server.enums.CategorieNameEnum.*;

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
