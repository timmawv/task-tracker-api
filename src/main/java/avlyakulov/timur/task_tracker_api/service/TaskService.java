package avlyakulov.timur.task_tracker_api.service;

import avlyakulov.timur.task_tracker_api.dto.TaskDto;
import avlyakulov.timur.task_tracker_api.entity.Task;

import java.util.List;

public interface TaskService {

    List<TaskDto> findTasksByUserId(Integer userId);

    Task findTaskByTaskIdAndUserId(String taskId, Integer userId);

    TaskDto createTaskByUserId(TaskDto taskDto, Integer userId);

    TaskDto editTask(TaskDto taskDto, Integer userId);

    TaskDto finishTask(String id, Integer userId);

    TaskDto restartTask(String id, Integer userId);
}