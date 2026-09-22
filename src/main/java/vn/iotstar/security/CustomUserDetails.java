package vn.iotstar.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.iotstar.entity.User;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();

        this.authorities = List.of(
                (GrantedAuthority) () -> user.getRole().getName()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}