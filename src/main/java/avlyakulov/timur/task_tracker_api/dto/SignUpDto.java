package avlyakulov.timur.task_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
}