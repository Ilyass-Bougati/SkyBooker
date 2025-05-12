package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.entity.*;
import skybooker.server.repository.BilletRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.repository.ReservationRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BilletControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BilletRepository billetRepository;

    @Autowired
    PassagerRepository passagerRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ObjectMapper objectMapper;

    Billet testBillet;
    Passager testPassager;
    Classe testClasse;
    Reservation testReservation;

    @BeforeEach
    void Setup() throws Exception{
        //billetRepository.deleteAll();
        //passagerRepository.deleteAll();
        //classeRepository.deleteAll();

        ///..
        testPassager = new Passager();
        testPassager.setNom("ELHILALI");
        testPassager.setPrenom("Hajar");
        testPassager.setCIN("W1234");
        testPassager.setAge(20);
        testPassager = passagerRepository.save(testPassager);

        testClasse = new Classe();
        testClasse.setNom("Business");
        testClasse.setPrixParKm(200);
        testClasse = classeRepository.save(testClasse);

        testReservation = new Reservation();
        //..
        testBillet = new Billet();
        testBillet.setSiege(24);
        testBillet.setClasse(testClasse);
        testBillet.setPassager(testPassager);
        testBillet.setReservation(testReservation);

    }
    @Test
    public void testGetAllBillets() throws Exception{
        mockMvc.perform(get("/api/v1/billet/"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetBilleById() throws Exception{
        mockMvc.perform(get("/api/v1/billet/" + testBillet.getId()))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateBillet() throws Exception{
        Billet billet = new Billet();
        testBillet.setSiege(30);
        testBillet.setClasse(testClasse);
        testBillet.setPassager(testPassager);
        testBillet.setReservation(testReservation);


        mockMvc.perform(post("/api/v1/billet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billet)))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateBillet() throws Exception{
        testBillet.setSiege(34);


        mockMvc.perform(put("/api/v1/billet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBillet)))
                .andExpect(status().isOk());

    }
    @Test
    public void testDeleteBillet() throws Exception{
        mockMvc.perform(delete("/api/v1/billet/"+testBillet.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/billet/" + testBillet.getId()))
                .andExpect(status().isNotFound());
    }
}
