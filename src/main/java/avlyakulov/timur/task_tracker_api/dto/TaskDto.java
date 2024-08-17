package avlyakulov.timur.task_tracker_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    private String id;

    private String title;

    private String description;

    private Boolean isCompleted;

    private Date createdAt;

    private Date finishedAt;
}