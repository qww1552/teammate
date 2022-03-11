package aftermealstudio.findteammate.mail;

import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.MemberAlreadyJoinedException;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.service.EmailAlarmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static aftermealstudio.findteammate.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlarmTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    RecruitmentRepository recruitmentRepository;
    @InjectMocks
    EmailAlarmService emailAlarmService;

    @Test
    @DisplayName("sendApplicationForJoin 메소드를 호출했을 때 해당하는 recruitment가 존재하지 않으면 예외가 발생한다.")
    void sendApplicationForJoinFailWithNotFoundTest() {
        Recruitment recruitment = getRecruitmentForTest();
        setAuthentication();
        
        when(memberRepository.findByUsername(any()))
                .thenReturn(java.util.Optional.ofNullable(getMemberForTest()));
        
        assertThrows(RecruitmentNotFoundException.class, () ->
                emailAlarmService.sendApplicationForJoin(recruitment.getId())
        );
    }

    @Test
    @DisplayName("sendApplicationForJoin 메소드를 호출했을 때 해당하는 recruitment에 현재 사용자가 이미 참여중일 경우 예외가 발생한다.")
    void sendApplicationForJoinFailWithAlreadyJoinedTest() {
        Recruitment recruitment = getRecruitmentForTest();
        setAuthentication();
        
        when(memberRepository.findByUsername(any()))
                .thenReturn(java.util.Optional.ofNullable(getMemberForTest()));
        when(recruitmentRepository.findById(recruitment.getId()))
                .thenReturn(java.util.Optional.of(recruitment));

        assertThrows(MemberAlreadyJoinedException.class, () ->
                emailAlarmService.sendApplicationForJoin(recruitment.getId())
        );
    }
}
