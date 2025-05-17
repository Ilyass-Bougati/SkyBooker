package skybooker.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.utils.Auth;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AeroportControllerTest {

    @Autowired
    MockMvc mvc;

    private static String adminToken = null;

    @BeforeEach
    public void setup() throws Exception {
        if (adminToken == null) {
            adminToken = Auth.login("ilyass@gmail.com", "123", mvc);
        }
    }


}
