package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.MemberAlreadyJoinedException;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class EmailAlarmService {

    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendApplicationForJoin(Long recruitmentId) throws MessagingException {
        Member currentMember = getCurrentMember();

        Recruitment recruitment = getRecruitmentOrThrowNotFound(recruitmentId);

        if (recruitment.getMembers().contains(currentMember))
            throw new MemberAlreadyJoinedException();

        sendMail(recruitment.getAuthor().getUsername(),
                "teammate 참여 신청 메일 : " + currentMember.getUsername(),
                getMailContent(recruitmentId, currentMember));
    }

    private String getMailContent(Long recruitmentId, Member currentMember) {
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

    private void sendMail(String email, String title, String content) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        helper.setTo(email);
        helper.setSubject(title);
        Context context = new Context();
        context.setVariable("requestUrl", content);
        String joinApplication = templateEngine.process("joinApplication", context);
        helper.setText(joinApplication, true);
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
