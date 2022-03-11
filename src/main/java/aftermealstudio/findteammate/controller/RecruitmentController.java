package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.recruitment.Create;
import aftermealstudio.findteammate.model.dto.recruitment.Response;
import aftermealstudio.findteammate.service.EmailAlarmService;
import aftermealstudio.findteammate.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final EmailAlarmService emailAlarmService;

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
    public ResponseEntity<Response> create(@RequestBody Create create) {
        Response response = recruitmentService.create(create);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{recruitmentId}/members")
    public ResponseEntity submitApplicationForJoin(@PathVariable Long recruitmentId) throws MessagingException {
        emailAlarmService.sendApplicationForJoin(recruitmentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{recruitmentId}/members/{username}")
    public ResponseEntity<Object> join(@PathVariable Long recruitmentId, @PathVariable String username) {
        recruitmentService.join(recruitmentId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recruitmentId}/members")
    public ResponseEntity<Object> unjoin(@PathVariable Long recruitmentId) {
        recruitmentService.unjoin(recruitmentId);
        return ResponseEntity.ok().build();
    }
}
