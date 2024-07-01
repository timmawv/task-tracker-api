package avlyakulov.timur.task_tracker_api.mapper;

import avlyakulov.timur.task_tracker_api.dto.SignUpDto;
import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);

    User signUpToUser(SignUpDto signUpDto);
}