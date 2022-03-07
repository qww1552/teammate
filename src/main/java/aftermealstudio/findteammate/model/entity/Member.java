package aftermealstudio.findteammate.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String verificationCode;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Builder
    public Member(
            String username,
            String password,
            Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void setVerificationCode(String code) {
        this.verificationCode = code;
    }

    public void verify() {
        this.accountNonLocked = true;
    }

    @PrePersist
    public void init() {
        this.accountNonExpired = true;
        this.accountNonLocked = false;
        this.enabled = true;
        this.credentialsNonExpired = true;
    }
}
