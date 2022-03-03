package aftermealstudio.findteammate.model.dto.member;

import aftermealstudio.findteammate.model.entity.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Preview {

    String username;

    public Preview(Member member) {
        this.username = member.getUsername();
    }
}
