package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;

    public Response create(Create create) {
        String username = AuthenticationUtil.getCurrentUsername();

        Member currentMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Recruitment recruitment = Recruitment.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .author(currentMember)
                .build();

        Recruitment save = recruitmentRepository.save(recruitment);

        return new Response(save);
    }
}
