package aftermealstudio.findteammate.model.dto.recruitment;

import aftermealstudio.findteammate.model.dto.member.Preview;
import aftermealstudio.findteammate.model.entity.Recruitment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Response {

    Long id;
    String title;
    String content;
    Preview author;

    public Response(Recruitment recruitment) {
        this.id = recruitment.getId();
        this.title = recruitment.getTitle();
        this.content = recruitment.getContent();
        this.author = new Preview(recruitment.getAuthor());
    }
}
