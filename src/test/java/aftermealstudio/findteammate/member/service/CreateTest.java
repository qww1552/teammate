package aftermealstudio.findteammate.member.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    String username = "username";
    String password = "password";
    Member member;

    @BeforeEach
    void setup() {
        member = Member.builder()
                .username(username)
                .password(password)
                .authorities(Set.of("ROLE_USER"))
                .build();
    }


//    @Test
//    @DisplayName("create 호출 시 생성된 member의 response dto를 반환한다.")
//    void createTest() {
//        when(memberRepository.save(any()))
//                .thenReturn(member);
//
//        Create create = new Create();
//        create.setUsername(username);
//        create.setPassword(password);
//
//        Response response = memberService.register(create);
//
//        Assertions.assertThat(response.getUsername()).isEqualTo(username);
//    }
}
