package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
//
//    @GetMapping
//    public ModelAndView create() {
//        return new ModelAndView("create-recruitment");
//    }

    @GetMapping
    public ResponseEntity<List<Response>> getAll() {
        List<Response> list = recruitmentService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{recruitmentId}")
    public ResponseEntity<Response> get(@PathVariable Long recruitmentId) {
        Response response = recruitmentService.findById(recruitmentId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> create(Create create) {
        Response response = recruitmentService.create(create);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{recruitmentId}/members")
    public ResponseEntity<Object> join(@PathVariable Long recruitmentId) {
        recruitmentService.join(recruitmentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recruitmentId}/members")
    public ResponseEntity<Object> unjoin(@PathVariable Long recruitmentId) {
        recruitmentService.unjoin(recruitmentId);
        return ResponseEntity.ok().build();
    }
}
