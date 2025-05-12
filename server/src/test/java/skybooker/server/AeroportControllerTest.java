package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Ville;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.repository.VilleRepository;
import skybooker.server.service.AeroportService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AeroportControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AeroportRepository aeroportRepository;
    @Autowired
    VilleRepository villeRepository;
    @Autowired
    AeroportService aeroportService;
    @Autowired
    ObjectMapper objectMapper;

    Aeroport testAeroport;
    Ville testVille;


    @BeforeEach
    void setUp(){
        aeroportRepository.deleteAll();
        villeRepository.deleteAll();
        testVille = new Ville();
        testVille.setNom("Casablanca");
        testVille.setPays("Maroc");
        testVille = villeRepository.save(testVille);
        testAeroport = new Aeroport();
        testAeroport.setNom("Casa Aeroport");
        testAeroport.setIataCode("ORY");
        testAeroport.setIcaoCode("LPFO");
        testAeroport.setLatitude(54.1234);
        testAeroport.setLongitude(43.678);
        testAeroport.setVille(testVille);
        testAeroport = aeroportRepository.save(testAeroport);
    }

    @Test
    public void testGetAllAeroports() throws Exception {
        mockMvc.perform(get("/api/v1/aeroport/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAeroportById() throws Exception{
        mockMvc.perform(get("/api/v1/aeroport/" + testAeroport.getId()))
                //.andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testeCreateAeroport() throws Exception{
        Aeroport aeroport = new Aeroport();
        aeroport.setNom("casa2 Aeroport");
        aeroport.setIataCode("RBT");
        aeroport.setIcaoCode("GMME");
        aeroport.setLatitude(5.935);
        aeroport.setLongitude(-10.423);
        aeroport.setVille(testVille);

        mockMvc.perform(post("/api/v1/aeroport/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aeroport)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateAeroport() throws Exception{

        testAeroport.setNom("Updated Aeroport");
        testAeroport.setIataCode("UDP");
        testAeroport.setIcaoCode("SAME");
        testAeroport.setLongitude(12.56);
        testAeroport.setLatitude(-2.01);

        mockMvc.perform(put("/api/v1/aeroport/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAeroport)))
                .andExpect(status().isOk());

    }

   @Test
   @WithMockUser(roles = "ADMIN")
    public void testDeleteAeroport() throws Exception{
        mockMvc.perform(delete("/api/v1/aeroport/"+testAeroport.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/aeroport/" + testAeroport.getId()))
                .andExpect(status().isNotFound());
    }
}