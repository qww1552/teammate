package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.model.dto.member.Create;
import aftermealstudio.findteammate.model.dto.member.Response;
import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Response create(Create create) {
        Member member = Member.builder()
                .username(create.getUsername())
                .password(passwordEncoder.encode(create.getPassword()))
                .build();

        Member save = memberRepository.save(member);

        return new Response(save);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
}
