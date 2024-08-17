package avlyakulov.timur.task_tracker_api.controller.auth;

import avlyakulov.timur.task_tracker_api.config.security.UserAuthProvider;
import avlyakulov.timur.task_tracker_api.config.security.validator.LoginAndPasswordValidator;
import avlyakulov.timur.task_tracker_api.dto.SignUpDto;
import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.dto.SignInDto;
import avlyakulov.timur.task_tracker_api.exceptions.AppException;
import avlyakulov.timur.task_tracker_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    private final LoginAndPasswordValidator loginAndPasswordValidator;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody SignInDto signInDto) {
        UserDto userDto = userService.login(signInDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        loginAndPasswordValidator.validate(signUpDto, bindingResult);
        if (bindingResult.hasErrors()) {
            String errors = getErrors(bindingResult);
            throw new AppException(errors, HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = userService.register(signUpDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }

    private String getErrors(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach(error -> sb.append(error.getDefaultMessage()));
        return sb.toString();
    }
}