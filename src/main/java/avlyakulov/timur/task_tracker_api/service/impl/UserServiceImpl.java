package avlyakulov.timur.task_tracker_api.service.impl;

import avlyakulov.timur.task_tracker_api.dto.SignUpDto;
import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.dto.SignInDto;
import avlyakulov.timur.task_tracker_api.entity.User;
import avlyakulov.timur.task_tracker_api.exceptions.AppException;
import avlyakulov.timur.task_tracker_api.mapper.UserMapper;
import avlyakulov.timur.task_tracker_api.repository.UserRepository;
import avlyakulov.timur.task_tracker_api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto login(SignInDto signInDto) {
        User user = userRepository.findByLogin(signInDto.getLogin())
                .orElseThrow(() -> new AppException("Login or password isn't correct", HttpStatus.BAD_REQUEST));

        if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword()))
            return userMapper.toUserDto(user);

        throw new AppException("Login or password isn't correct", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) {
        User user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        try {
            User savedUser = userRepository.save(user);
            return userMapper.toUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("User with such login already exist", HttpStatus.BAD_REQUEST);
        }
    }
}