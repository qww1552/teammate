package aftermealstudio.findteammate.recruitment.repository;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.repository.MemberRepository;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CreateTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("recruitment를 생성하면 작성자가 members에 추가된다.")
    void createTest() {
        Member member = Member.builder()
                .username("username")
                .password("password")
                .build();

        memberRepository.save(member);

        Recruitment recruitment = Recruitment.builder()
                .title("title")
                .content("content")
                .author(member)
                .build();

        recruitmentRepository.save(recruitment);

        Recruitment byAuthor = recruitmentRepository.findByAuthor(member).get();

        Assertions.assertThat(byAuthor.getMembers()).contains(member);
    }
}
