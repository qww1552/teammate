package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @GetMapping
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create-recruitment");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(Create create) {
        Response response = recruitmentService.create(create);

        ModelAndView modelAndView = new ModelAndView("create-recruitment");
        return modelAndView;
    }

    @PatchMapping("/{recruitmentId}/members")
    public ModelAndView join(@PathVariable Long recruitmentId) {
        recruitmentService.join(recruitmentId);
        return new ModelAndView("hello");
    }

    @DeleteMapping("/{recruitmentId}/members")
    public ModelAndView unjoin(@PathVariable Long recruitmentId) {
        recruitmentService.unjoin(recruitmentId);
        return new ModelAndView("hello");
    }
}
