package skybooker.server.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record DefaultAdminProperties(String email, String password) {
}
