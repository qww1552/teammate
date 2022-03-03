package aftermealstudio.findteammate.repository;

import aftermealstudio.findteammate.model.entity.Member;
import aftermealstudio.findteammate.model.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findByAuthor(Member author);

    List<Recruitment> findAll();
}
