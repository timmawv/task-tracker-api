package avlyakulov.timur.task_tracker_api.config.security;

import avlyakulov.timur.task_tracker_api.dto.UserDto;
import avlyakulov.timur.task_tracker_api.entity.User;
import avlyakulov.timur.task_tracker_api.exceptions.AppException;
import avlyakulov.timur.task_tracker_api.mapper.UserMapper;
import avlyakulov.timur.task_tracker_api.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);//will be alive for one hour

        return JWT.create()
                .withIssuer(userDto.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", userDto.getFirstName())
                .withClaim("lastName", userDto.getLastName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        UserDto user = UserDto.builder()
                .login(decoded.getIssuer())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        User user = userRepository.findByLogin(decoded.getIssuer())
                .orElseThrow(() -> new AppException("Uknown user", HttpStatus.NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(user), null, Collections.emptyList());
    }
}