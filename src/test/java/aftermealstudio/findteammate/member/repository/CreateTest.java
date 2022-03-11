package aftermealstudio.findteammate.member.repository;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@DataJpaTest
public class CreateTest {

    @Autowired
    MemberRepository memberRepository;

    String username = "username";
    String password = "password";

    @BeforeEach
    void setup() {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .authorities(Set.of("ROLE_USER"))
                .build();

        memberRepository.save(member);
    }

    @Test
    @DisplayName("member 저장 시 필드에 기본값이 저장된다.")
    void createMemberFieldTest() {
        Member byUsername = memberRepository.findByUsername(username)
                .get();

        Assertions.assertThat(byUsername.isAccountNonExpired()).isTrue();
        Assertions.assertThat(byUsername.isAccountNonLocked()).isFalse(); // 인증을 받지 않으면 false
        Assertions.assertThat(byUsername.isCredentialsNonExpired()).isTrue();
        Assertions.assertThat(byUsername.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("member 에게는 생성 시 지정된 권한이 부여된다.")
    void createMemberAuthorityTest() {
        Member byUsername = memberRepository.findByUsername(username)
                .get();

        for (GrantedAuthority authority : byUsername.getAuthorities()){
            Assertions.assertThat(authority.getAuthority()).isEqualTo("ROLE_USER");
        }
    }
}
