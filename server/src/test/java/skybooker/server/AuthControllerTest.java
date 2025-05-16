package skybooker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import skybooker.server.DTO.RegisterRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void login() throws Exception {
        mvc.perform(post("/api/v1/auth/login").with(httpBasic("ilyass@gmail.com", "123")))
                .andExpect(status().isOk());
    }

    @Test
    void loginNeeded() throws Exception {
        mvc.perform(post("/api/v1/aeroport/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
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
    void checkAuthentication() throws Exception {
        ResultActions res = mvc.perform(post("/api/v1/auth/login").with(httpBasic("ilyass@gmail.com", "123")))
                .andExpect(status().isOk());
        String token = res.andReturn().getResponse().getContentAsString();

        mvc.perform(get("/api/v1/aeroport/").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}
