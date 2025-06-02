package skybooker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import skybooker.server.records.DefaultAdminProperties;
import skybooker.server.records.RsaKeyProperties;

@EnableConfigurationProperties({
        RsaKeyProperties.class,
        DefaultAdminProperties.class
})
@SpringBootApplication
@EnableScheduling
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
