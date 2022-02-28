package aftermealstudio.findteammate.recruitment.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.service.RecruitmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JoinTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    RecruitmentRepository recruitmentRepository;
    @InjectMocks
    RecruitmentService recruitmentService;
    Member author;

    @BeforeEach
    void setup() {
        author = Member.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("join 메소드를 호출하면 해당 recruitment에 현재 사용자가 추가된다.")
    void joinTest() {
        Recruitment recruitment = Recruitment.builder()
                .title("title")
                .content("content")
                .author(author)
                .build();

        recruitment.init();

        when(recruitmentRepository.findById(any()))
                .thenReturn(java.util.Optional.ofNullable(recruitment));

        Member member = Member.builder()
                .username("joinedMember")
                .password("password")
                .build();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        member.getUsername(),
                        member.getPassword()
                )
        );
        when(memberRepository.findByUsername(any()))
                .thenReturn(Optional.ofNullable(member));
        recruitmentService.join(recruitment.getId());

        Assertions.assertThat(recruitment.getMembers()).contains(member);
    }
}
