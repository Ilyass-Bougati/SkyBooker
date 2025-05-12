package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.entity.Avion;
import skybooker.server.entity.Capacite;
import skybooker.server.entity.Classe;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.AvionRepository;
import skybooker.server.repository.CapaciteRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.CompanieAerienneRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CapaciteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CapaciteRepository capaciteRepository;
    @Autowired
    AvionRepository avionRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    CompanieAerienneRepository companieAerienneRepository;
    @Autowired
    ObjectMapper objectMapper;

    Capacite testCapacite;
    Avion testAvion;
    Classe testClasse;
    CompanieAerienne testCompanieAerienne;

    @BeforeEach
    void setUp() throws Exception{
        testCompanieAerienne = new CompanieAerienne();
        testCompanieAerienne.setNom("Royal Air Maroc");
        testCompanieAerienne.setIataCode("AT");
        testCompanieAerienne.setIcaoCode("RAM");
        testCompanieAerienne = companieAerienneRepository.save(testCompanieAerienne);

        testAvion = new Avion();
        testAvion.setModel("Boeing ");
        testAvion.setIataCode("73H");
        testAvion.setIcaoCode("B738");
        testAvion.setMaxDistance(6789);
        testAvion.setCompanieAerienne(testCompanieAerienne);

        testClasse = new Classe();
        testClasse.setNom("Business");
        testClasse.setPrixParKm(200);

        testCapacite = new Capacite();
        testCapacite.setCapacite(276);
        testCapacite.setBorneInf(29);
        testCapacite.setBorneSup(290);
        testCapacite.setClasse(testClasse);
        testCapacite.setAvion(testAvion);
        testCapacite = capaciteRepository.save(testCapacite);

    }

    @Test
    public void testGetAllCapacites() throws Exception{
        mockMvc.perform(get("/api/v1/capacite/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetCapaciteById() throws Exception{
        mockMvc.perform(get("/api/v1/capacite/" + testCapacite.getId()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void CreateCapacite() throws Exception{
        Capacite capacite = new Capacite();
        capacite.setCapacite(300);
        capacite.setBorneInf(30);
        capacite.setBorneSup(310);
        capacite.setClasse(testClasse);
        capacite.setAvion(testAvion);


        mockMvc.perform(post("/api/v1/capacite/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(capacite)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCapacite() throws Exception{
        testCapacite.setCapacite(323);
        testCapacite.setBorneInf(12);
        testCapacite.setBorneSup(330);

        mockMvc.perform(put("/api/v1/capacite/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCapacite)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCapacite() throws Exception{
        mockMvc.perform(delete("/api/v1/capacite/"+testCapacite.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/capacite/" + testCapacite.getId()))
                .andExpect(status().isNotFound());
    }

}
