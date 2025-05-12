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
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.CompanieAerienneRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanieAerienneControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompanieAerienneRepository companieAerienneRepository;
    @Autowired
    ObjectMapper objectMapper;

    CompanieAerienne testCompanieAerienne;

    @BeforeEach
    void setUp(){
        testCompanieAerienne = new CompanieAerienne();

        testCompanieAerienne = new CompanieAerienne();
        testCompanieAerienne.setNom("Royal Air Maroc");
        testCompanieAerienne.setIataCode("AT");
        testCompanieAerienne.setIcaoCode("RAM");
        testCompanieAerienne = companieAerienneRepository.save(testCompanieAerienne);
    }
    @Test
    public void testGetAllCompanieAerienne() throws  Exception{
        mockMvc.perform(get("/api/v1/companie-aerienne/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetCompanieAerienneById() throws Exception{
        mockMvc.perform(get("/api/v1/companie-aerienne/" + testCompanieAerienne.getId()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCompanieAerienne() throws Exception{
        CompanieAerienne companieAerienne = new CompanieAerienne();
        companieAerienne.setNom("Air France");
        companieAerienne.setIataCode("BT");
        companieAerienne.setIcaoCode("ATD");

        mockMvc.perform(post("/api/v1/companie-aerienne/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companieAerienne)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCompanieAerienne() throws Exception{
        testCompanieAerienne.setNom("Air USA");
        testCompanieAerienne.setIataCode("SA");
        testCompanieAerienne.setIcaoCode("ABC");

        mockMvc.perform(put("/api/v1/companie-aerienne/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCompanieAerienne)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteComapanieAerienne() throws Exception{
        mockMvc.perform(delete("/api/v1/companie-aerienne/"+testCompanieAerienne.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/companie-aerienne/" + testCompanieAerienne.getId()))
                .andExpect(status().isNotFound());
    }
}
