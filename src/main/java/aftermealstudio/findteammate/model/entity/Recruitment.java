package aftermealstudio.findteammate.model.entity;

import aftermealstudio.findteammate.model.exception.MemberAlreadyJoinedException;
import aftermealstudio.findteammate.model.exception.MemberDoesNotJoinedException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "recruitment")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    private Member author;

    @ManyToMany
    private List<Member> members;

    @PrePersist
    public void init() {
        members = new ArrayList<>();
        members.add(author);
    }

    @Builder
    public Recruitment(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void add(Member member) {
        if (members.contains(member))
            throw new MemberAlreadyJoinedException();
        else
            this.members.add(member);
    }

    public void remove(Member member) {
        if (!members.contains(member))
            throw new MemberDoesNotJoinedException();
        else
            members.remove(member);
    }
}
