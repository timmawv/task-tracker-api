package avlyakulov.timur.task_tracker_api.service;

import avlyakulov.timur.task_tracker_api.dto.SignUpDto;
import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.dto.SignInDto;

public interface UserService {

    UserDto login(SignInDto signInDto);

    UserDto register(SignUpDto signUpDto);
}
