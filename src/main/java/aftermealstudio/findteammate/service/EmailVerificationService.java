package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.exception.MemberNotFoundException;
import aftermealstudio.findteammate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;

    @Transactional
    public void sendVerification(String email) {
        Member member = getMember(email);
        String code = generateCode();
        member.setVerificationCode(code);
        sendMail(email, "teammate 인증 메일", makeMailText(member));
    }

    private String generateCode() {
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int n = (int) (Math.random() * 10);
            code.append(n);
        }
        return code.toString();
    }


    @Transactional
    public boolean confirm(String email, String verificationCode) {
        Member member = getMember(email);
        if (verificationCode.equals(member.getVerificationCode())) {
            member.verify();
            return true;
        }
        return false;
    }


    private Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }


    private void sendMail(String to, String title, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }


    private String makeMailText(Member member) {
        StringBuffer mailText = new StringBuffer(
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        );
        mailText.append("/api/auth/verify?");
        mailText.append("email=");
        mailText.append(member.getUsername());
        mailText.append("&code=");
        mailText.append(member.getVerificationCode());

        return mailText.toString();
    }
}