package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.utils.Auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest {

    @Autowired
    MockMvc mvc;

    private static String adminToken = null;

    @BeforeEach
    public void setup() throws Exception {
        if (adminToken == null) {
            adminToken = Auth.login("ilyass@gmail.com", "123", mvc);
        }
    }

    @Test
    @Order(1)
    void getCurrentClient() throws Exception {
        mvc.perform(get("/api/v1/client/").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }


    @Test
    @Order(2)
    void modifyClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAdresse("aaa");
        clientDTO.setEmail("ilyass@gmail.com");
        clientDTO.setTelephone("123");

        mvc.perform(
                        put("/api/v1/client/")
                                .header("Authorization", "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDTO))
                )
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void deleteClient() throws Exception {
        String clientToken = Auth.login("amime@gmail.com", "123", mvc);
        mvc.perform(delete("/api/v1/client/").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void getUserUnauthorized() throws Exception {
        String clientToken = Auth.login("amine@gmail.com", "123", mvc);
        mvc.perform(get("/api/v1/client/5").header("Authorization", "Bearer " + clientToken))
                .andExpect(status().is(403));
    }
}
