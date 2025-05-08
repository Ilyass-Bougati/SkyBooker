package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import skybooker.server.entity.*;
import skybooker.server.repository.*;
import skybooker.server.service.ClientService;
import skybooker.server.service.VilleService;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CompanieAerienneRepository companieAerienneRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Override
    public void run(String... args) throws Exception {
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

        categorieRepository.save(enfant);
        categorieRepository.save(senior);
        categorieRepository.save(normale);

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

        // Creating a default city
        Ville settat = new Ville();
        settat.setNom("settat");
        settat.setPays("maroc");
        villeRepository.save(settat);

        // Creating default companieaerieen
        CompanieAerienne companie = new CompanieAerienne();
        companie.setNom("companie");
        companieAerienneRepository.save(companie);

        // creating airports
        Aeroport depart = new Aeroport();
        depart.setNom("depart");
        depart.setIcaoCode("icao1");
        depart.setIataCode("iata1");
        depart.setLatitude(0.0);
        depart.setLongitude(0.0);
        depart.setVille(settat);

        Aeroport arrive = new Aeroport();
        arrive.setNom("arrive");
        arrive.setIcaoCode("icao2");
        arrive.setIataCode("iata2");
        arrive.setLatitude(1.0);
        arrive.setLongitude(1.0);
        arrive.setVille(settat);

        aeroportRepository.save(depart);
        aeroportRepository.save(arrive);

        // creatign an airplane
        Avion avion = new Avion();
        avion.setCompanieAerienne(companie);
        avion.setMaxDistance(1000);
        avion.setModel("boeing");
        avion.setIataCode("iata3");
        avion.setIcaoCode("icao3");
        avionRepository.save(avion);

        // classe
        Classe classe = new Classe();
        classe.setNom("premier");
        classe.setPrixParKm(100);

        Classe eco = new Classe();
        eco.setNom("economie");
        eco.setPrixParKm(100);

        classeRepository.save(classe);
        classeRepository.save(eco);
    }
}
