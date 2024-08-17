package avlyakulov.timur.task_tracker_api.mapper;

import avlyakulov.timur.task_tracker_api.dto.TaskDto;
import avlyakulov.timur.task_tracker_api.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    List<TaskDto> toListTaskDto(List<Task> tasks);

    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToDto(Task task);
}
