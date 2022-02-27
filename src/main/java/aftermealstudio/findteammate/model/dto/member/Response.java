package aftermealstudio.findteammate.model.dto.member;

import aftermealstudio.findteammate.model.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Response {

    private String username;

    public Response(Member member) {
        this.username = member.getUsername();
    }
}

