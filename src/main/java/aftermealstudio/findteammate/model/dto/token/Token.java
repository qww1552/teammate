package aftermealstudio.findteammate.model.dto.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String access;
    private String refresh;

    @Builder
    public Token(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }
}
