package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
