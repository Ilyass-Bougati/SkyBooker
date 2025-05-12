package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Passager;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.PassagerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PassagerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PassagerRepository passagerRepository;
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    ObjectMapper objectMapper;

    Passager testPassager;
    Categorie testCategorie;

    @BeforeEach
    void setUp() {
        testCategorie = new Categorie();
        testCategorie.setNom("");
        testPassager = new Passager();


    }
    @Test
    public void testGetAllPassager() throws Exception{
        mockMvc.perform(get("/api/v1/passager/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetPassagerById() throws Exception{
        mockMvc.perform(get("/api/v1/passager/" + testPassager.getId()))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreatePassager() throws  Exception{

    }
    @Test
    public void testUpdatePassager() throws Exception{}
    @Test
    public void testDeletePassager() throws Exception{
        mockMvc.perform(delete("/api/v1/passager/"+testPassager.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/passager/" + testPassager.getId()))
                .andExpect(status().isNotFound());
    }
}
