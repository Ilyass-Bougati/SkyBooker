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
import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.AvionRepository;
import skybooker.server.repository.CompanieAerienneRepository;
import skybooker.server.service.AvionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AvionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AvionRepository avionRepository;

    @Autowired
    CompanieAerienneRepository companieAerienneRepository;

    @Autowired
    ObjectMapper objectMapper;

    Avion testAvion;
    CompanieAerienne testCompanieAerienne;
    @BeforeEach
    void setUp(){
        avionRepository.deleteAll();
        companieAerienneRepository.deleteAll();

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
        testAvion = avionRepository.save(testAvion);
    }
    @Test
    public void testGetAllAvions() throws Exception{
        mockMvc.perform(get("/api/v1/avion/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAvionById() throws Exception{
        mockMvc.perform(get("/api/v1/avion/" + testAvion.getId()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateAvion() throws Exception{
        Avion avion = new Avion();
        avion.setModel("Airbus 10");
        avion.setIataCode("CBA");
        avion.setIcaoCode("A100");
        avion.setMaxDistance(4567);
        avion.setCompanieAerienne(testCompanieAerienne);

        mockMvc.perform(post("/api/v1/avion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avion)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testupdateAvion() throws Exception{

        testAvion.setModel("Boeing 73");
        testAvion.setIataCode("7A8");
        testAvion.setIataCode("12D3");
        testAvion.setMaxDistance(6543);


        mockMvc.perform(put("/api/v1/avion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAvion)))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteAvion() throws Exception{
        mockMvc.perform(delete("/api/v1/avion/"+testAvion.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/avion/" + testAvion.getId()))
                .andExpect(status().isNotFound());
    }
}
