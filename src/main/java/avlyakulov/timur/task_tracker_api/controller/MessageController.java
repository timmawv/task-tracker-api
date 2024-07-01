package avlyakulov.timur.task_tracker_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MessageController {

    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages(@AuthenticationPrincipal Object user) {
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }
}