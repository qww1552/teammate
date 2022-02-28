package aftermealstudio.findteammate.recruitment.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.MemberAlreadyJoinedException;
import aftermealstudio.findteammate.model.exception.MemberDoesNotJoinedException;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void joinSuccessTest() {
        Recruitment recruitment = getRecruitmentForTest();

        Member member = getMemberForTest();

        setAuthentication(member);

        when(recruitmentRepository.findById(any()))
                .thenReturn(java.util.Optional.of(recruitment));
        when(memberRepository.findByUsername(any()))
                .thenReturn(Optional.of(member));

        recruitmentService.join(recruitment.getId());

        Assertions.assertThat(recruitment.getMembers()).contains(member);
    }

    @Test
    @DisplayName("join 메소드를 호출했을 때 해당 recruitment에 해당 사용자가 이미 참여중이라면 예외가 발생한다.")
    void joinFailTest() {
        Recruitment recruitment = getRecruitmentForTest();

        Member member = getMemberForTest();

        recruitment.add(member);
        setAuthentication(member);

        when(recruitmentRepository.findById(any()))
                .thenReturn(java.util.Optional.of(recruitment));
        when(memberRepository.findByUsername(any()))
                .thenReturn(Optional.of(member));

        assertThrows(MemberAlreadyJoinedException.class, () ->
                recruitmentService.join(recruitment.getId())
        );
    }

    @Test
    @DisplayName("unjoin 메소드를 호출하면 해당 recruitment에서 참여중인 현재 멤버가 제거된다.")
    void unJoinSuccessTest() {
        Recruitment recruitment = getRecruitmentForTest();

        Member member = getMemberForTest();
        recruitment.add(member);

        setAuthentication(member);

        when(recruitmentRepository.findById(any()))
                .thenReturn(java.util.Optional.of(recruitment));
        when(memberRepository.findByUsername(any()))
                .thenReturn(Optional.of(member));

        recruitmentService.unjoin(recruitment.getId());

        Assertions.assertThat(recruitment.getMembers()).doesNotContain(member);
    }

    @Test
    @DisplayName("unjoin 메소드를 호출했을 때 해당 멤버가 참여중이 아니면 예외가 발생한다.")
    void unJoinFailTest() {
        Recruitment recruitment = getRecruitmentForTest();

        Member member = getMemberForTest();

        setAuthentication(member);

        when(recruitmentRepository.findById(any()))
                .thenReturn(java.util.Optional.of(recruitment));
        when(memberRepository.findByUsername(any()))
                .thenReturn(Optional.of(member));

        assertThrows(MemberDoesNotJoinedException.class, () ->
            recruitmentService.unjoin(recruitment.getId())
        );
    }

    @Test
    @DisplayName("unjoin 메소드를 호출했을 때 해당 recruitment가 존재하지 않으면 예외가 발생한다.")
    void unJoinFailWithNotFoundTest() {
        Member member = getMemberForTest();

        setAuthentication(member);

        assertThrows(RecruitmentNotFoundException.class, () ->
            recruitmentService.unjoin(any())
        );
    }

    @Test
    @DisplayName("join 메소드를 호출했을 때 해당 recruitment가 존재하지 않으면 예외가 발생한다.")
    void joinFailWithNotFoundTest() {
        Member member = getMemberForTest();

        setAuthentication(member);

        assertThrows(RecruitmentNotFoundException.class, () ->
            recruitmentService.join(any())
        );
    }


    private void setAuthentication(Member member) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        member.getUsername(),
                        member.getPassword()
                )
        );
    }

    private Member getMemberForTest() {
        return Member.builder()
                .username("joinedMember")
                .password("password")
                .build();
    }

    private Recruitment getRecruitmentForTest() {
        Recruitment recruitment = Recruitment.builder()
                .title("title")
                .content("content")
                .author(author)
                .build();

        recruitment.init();
        
        return recruitment;
    }
}
