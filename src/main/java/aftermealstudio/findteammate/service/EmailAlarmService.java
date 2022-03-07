package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.MemberAlreadyJoinedException;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class EmailAlarmService {

    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;

    public void sendApplicationForJoin(Long recruitmentId) {
        Member currentMember = getCurrentMember();

        Recruitment recruitment = getRecruitmentOrThrowNotFound(recruitmentId);

        if (recruitment.getMembers().contains(currentMember))
            throw new MemberAlreadyJoinedException();

        sendMail(recruitment.getAuthor().getUsername(),
                "teammate 참여 신청 메일 : " + currentMember.getUsername(),
                getMailText(recruitmentId, currentMember));
    }

    private String getMailText(Long recruitmentId, Member currentMember) {
        StringBuffer mailText = new StringBuffer(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath().build().toUriString()
        );

        mailText.append("/recruitments/");
        mailText.append(recruitmentId);
        mailText.append("/members/");
        mailText.append(currentMember.getUsername());

        return mailText.toString();
    }

    private void sendMail(String email, String title, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject(title);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

    private Recruitment getRecruitmentOrThrowNotFound(Long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId).orElseThrow(
                RecruitmentNotFoundException::new);
    }

    private Member getCurrentMember() {
        String username = AuthenticationUtil.getCurrentUsername();

        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
