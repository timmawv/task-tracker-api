package avlyakulov.timur.task_tracker_api.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String login;

    private String token;

}
