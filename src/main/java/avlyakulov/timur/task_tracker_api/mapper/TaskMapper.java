package avlyakulov.timur.task_tracker_api.mapper;

import avlyakulov.timur.task_tracker_api.dto.TaskDto;
import avlyakulov.timur.task_tracker_api.entity.Task;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToDto(Task task);
}
