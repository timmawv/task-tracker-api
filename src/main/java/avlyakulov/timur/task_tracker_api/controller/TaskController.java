package avlyakulov.timur.task_tracker_api.controller;

import avlyakulov.timur.task_tracker_api.dto.TaskDto;
import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.entity.Task;
import avlyakulov.timur.task_tracker_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getListTask(@AuthenticationPrincipal UserDto userDto) {
        List<Task> tasks = taskService.findTasksByUserId(userDto.getId());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@AuthenticationPrincipal UserDto userDto, @PathVariable("id") String id) {
        Task taskById = taskService.findTaskByTaskIdAndUserId(id, userDto.getId());
        return ResponseEntity.ok(taskById);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@AuthenticationPrincipal UserDto userDto, @RequestBody TaskDto taskDto) {
        TaskDto taskByUserId = taskService.createTaskByUserId(taskDto, userDto.getId());
        return ResponseEntity.ok(taskByUserId);
    }
}