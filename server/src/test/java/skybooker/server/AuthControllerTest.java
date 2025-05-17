package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.utils.Auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mvc;


    @Test
    @Order(1)
    void login() throws Exception {
        Auth.login("ilyass@gmail.com", "123", mvc);
    }

    @Test
    @Order(2)
    void loginNeeded() throws Exception {
        mvc.perform(post("/api/v1/aeroport/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void registerClient() throws Exception {
        RegisterRequestDTO requestContent = new RegisterRequestDTO();
        requestContent.setEmail("newClient@gmail.com");
        requestContent.setPassword("123");
        requestContent.setAdresse("adresseAmine");
        requestContent.setCIN("cinAmine");
        requestContent.setAge(5);
        requestContent.setTelephone("0000");
        requestContent.setPrenom("Amine");
        requestContent.setNom("Sidki");

        mvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestContent))
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void checkAuthentication() throws Exception {
        String token = Auth.login("ilyass@gmail.com", "123", mvc);

        mvc.perform(get("/api/v1/aeroport/").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}
