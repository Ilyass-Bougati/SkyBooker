package skybooker.server.utils;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class Auth {
    public static String login(String username, String password, MockMvc mvc) throws Exception {
        Logger logger = LogManager.getLogManager().getLogger("Auth");
        ResultActions res = mvc.perform(
                post("/api/v1/auth/login")
                        .with(httpBasic(username, password))
        );

        // checking if the response is ok
        if (res.andReturn().getResponse().getStatus() == 200) {
            return res.andReturn().getResponse().getContentAsString();
        } else {
            return null;
        }
    }
}
