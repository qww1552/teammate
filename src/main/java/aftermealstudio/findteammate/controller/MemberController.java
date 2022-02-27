package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.member.Create;
import aftermealstudio.findteammate.model.dto.member.Response;
import aftermealstudio.findteammate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ModelAndView create() {
        return new ModelAndView("create-member");
    }

    @PostMapping
    public ModelAndView test(Create create) {
        Response response = memberService.create(create);
        return new ModelAndView("home");
    }
}
