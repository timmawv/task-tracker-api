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
    public List<TaskDto> findTasksByUserId(Integer userId) {
        return taskMapper.toListTaskDto(taskRepository.findAllByOwnerId(userId));
    }

    @Override
    public Task findTaskByTaskIdAndUserId(String taskId, Integer userId) {
        return taskRepository.findTaskByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new AppException("The task with such id doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public TaskDto createTaskByUserId(TaskDto taskDto, Integer userId) {
        Task task = taskMapper.taskDtoToTask(taskDto);
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(Date.from(Instant.now()));
        task.setIsCompleted(false);
        task.setOwner(new User(userId));
        taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }

    @Override
    public TaskDto editTask(TaskDto taskDto, Integer userId) {
        Task task = findTaskByTaskIdAndUserId(taskDto.getId(), userId);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }

    @Override
    public TaskDto finishTask(String id, Integer userId) {
        Task task = findTaskByTaskIdAndUserId(id, userId);
        task.setFinishedAt(Date.from(Instant.now()));
        task.setIsCompleted(true);
        taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }

    @Override
    public TaskDto restartTask(String id, Integer userId) {
        Task task = findTaskByTaskIdAndUserId(id, userId);
        task.setFinishedAt(null);
        task.setIsCompleted(false);
        taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }
}