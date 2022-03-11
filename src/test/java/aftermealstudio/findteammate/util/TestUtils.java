package aftermealstudio.findteammate.util;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public class TestUtils {

    public static void setAuthentication() {
        Member author = getAuthor();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        author.getUsername(),
                        author.getPassword()
                )
        );
    }

    private static Member getAuthor() {
        Member author = Member.builder()
                .username("author")
                .password("author")
                .authorities(Collections.singleton("ROLE_USER"))
                .build();
        return author;
    }

    public static Member getMemberForTest() {
        return Member.builder()
                .username("joinedMember")
                .password("password")
                .build();
    }

    public static Recruitment getRecruitmentForTest() {
        Recruitment recruitment = Recruitment.builder()
                .title("title")
                .content("content")
                .author(getAuthor())
                .build();

        recruitment.init();

        return recruitment;
    }
}
