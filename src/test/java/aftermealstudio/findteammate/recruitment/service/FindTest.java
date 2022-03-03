package aftermealstudio.findteammate.recruitment.service;

import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import aftermealstudio.findteammate.model.exception.RecruitmentNotFoundException;
import aftermealstudio.findteammate.repository.RecruitmentRepository;
import aftermealstudio.findteammate.service.RecruitmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindTest {

    @Mock
    RecruitmentRepository recruitmentRepository;
    @InjectMocks
    RecruitmentService recruitmentService;
    Member author;

    @BeforeEach
    void setup() {
        author = Member.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("findAll을 호출하면 Response dto로 변환된 결과가 반환된다.")
    void findAllTest() {
        Recruitment recruitment1 = Recruitment.builder()
                .title("1")
                .content("1")
                .author(author)
                .build();
        Recruitment recruitment2 = Recruitment.builder()
                .title("2")
                .content("2")
                .author(author)
                .build();

        when(recruitmentRepository.findAll())
                .thenReturn(List.of(recruitment1, recruitment2));

        List<Response> all = recruitmentService.findAll();

        Assertions.assertThat(all.get(0)).isEqualTo(new Response(recruitment1));
        Assertions.assertThat(all.get(1)).isEqualTo(new Response(recruitment2));
    }

    @Test
    @DisplayName("findById를 호출하면 Response dto로 변환된 결과가 반환된다.")
    void findByIdSuccessTest() {
        Long id = 1L;
        Recruitment recruitment = Recruitment.builder()
                .id(id)
                .title("title")
                .content("test")
                .author(author)
                .build();

        when(recruitmentRepository.findById(id))
                .thenReturn(Optional.of(recruitment));

        Response byId = recruitmentService.findById(id);

        Assertions.assertThat(byId).isEqualTo(new Response(recruitment));
    }

    @Test
    @DisplayName("findById를 호출했을 때 존재하지 않은 recruitment일 경우 예외가 발생한다..")
    void findByIdFailTest() {
        Long id = 1L;

        when(recruitmentRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(RecruitmentNotFoundException.class, () ->
                recruitmentService.findById(id)
        );
    }

}
