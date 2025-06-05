package skybooker.server.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import skybooker.server.entity.Client;
import skybooker.server.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private final Client client;

    public UserDetailsImpl(Client client) {
        this.client = client;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(client.getRole());
        return roleList;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return client.getEmail();
    }
}
