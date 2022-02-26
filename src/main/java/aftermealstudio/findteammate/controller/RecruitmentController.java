package aftermealstudio.findteammate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recruitments")
public class RecruitmentController {

    @GetMapping()
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create-recruitment");
        return modelAndView;
    }
}
