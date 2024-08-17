package avlyakulov.timur.task_tracker_api.service.impl;

import avlyakulov.timur.task_tracker_api.dto.TaskDto;
import avlyakulov.timur.task_tracker_api.entity.Task;
import avlyakulov.timur.task_tracker_api.entity.User;
import avlyakulov.timur.task_tracker_api.exceptions.AppException;
import avlyakulov.timur.task_tracker_api.mapper.TaskMapper;
import avlyakulov.timur.task_tracker_api.repository.TaskRepository;
import avlyakulov.timur.task_tracker_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    public List<Task> findTasksByUserId(Integer userId) {
        return taskRepository.findAllByOwnerId(userId);
    }

    @Override
    public Task findTaskByTaskIdAndUserId(String taskId, Integer userId) {
        return taskRepository.findTaskByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new AppException("The task with such id doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public TaskDto createTaskByUserId(TaskDto taskDto, Integer userId) {
        Task task = taskMapper.taskDtoToTask(taskDto);
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        task.setId(uuidAsString);
        task.setCreatedAt(Date.from(Instant.now()));
        task.setIsCompleted(false);
        task.setOwner(new User(userId));
        taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }
}