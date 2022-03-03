package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;

    public Response create(Create create) {
        Member currentMember = getCurrentMember();

        Recruitment recruitment = Recruitment.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .author(currentMember)
                .build();

        Recruitment save = recruitmentRepository.save(recruitment);

        return new Response(save);
    }

    public List<Response> findAll() {
        return recruitmentRepository.findAll().stream()
                .map(Response::new)
                .collect(Collectors.toList());
    }

    public Response findById(Long recruitmentId) {
        Recruitment recruitment = getRecruitmentOrThrowNotFound(recruitmentId);
        return new Response(recruitment);
    }

    @Transactional
    public void join(Long recruitmentId) {
        Recruitment recruitment = getRecruitmentOrThrowNotFound(recruitmentId);

        recruitment.add(getCurrentMember());
    }

    @Transactional
    public void unjoin(Long recruitmentId) {
        Recruitment recruitment = getRecruitmentOrThrowNotFound(recruitmentId);

        recruitment.remove(getCurrentMember());
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
