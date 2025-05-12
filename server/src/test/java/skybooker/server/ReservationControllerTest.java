package skybooker.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import skybooker.server.repository.ReservationRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ReservationRepository reservationRepository;

}
