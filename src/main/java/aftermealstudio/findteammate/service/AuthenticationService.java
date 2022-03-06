package aftermealstudio.findteammate.service;

import aftermealstudio.findteammate.jwt.JwtTokenProvider;
import aftermealstudio.findteammate.model.dto.member.Create;
import aftermealstudio.findteammate.model.dto.member.Login;
import aftermealstudio.findteammate.model.dto.member.Response;
import aftermealstudio.findteammate.model.dto.token.Token;
import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.exception.MemberAlreadyExistException;
import aftermealstudio.findteammate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Token login(Login login) {
        String username = login.getUsername();
        String password = login.getPassword();

        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        Token token = tokenProvider.createToken(authenticate);

        return token;
    }

    public Response register(Create create) {
        memberRepository.findByUsername(create.getUsername())
                .ifPresent((member)-> {
                    throw new MemberAlreadyExistException();
                }
        );

        Member member = Member.builder()
                .username(create.getUsername())
                .password(passwordEncoder.encode(create.getPassword()))
                .authorities(Set.of("ROLE_USER"))
                .build();

        Member save = memberRepository.save(member);

        return new Response(save);
    }
}
