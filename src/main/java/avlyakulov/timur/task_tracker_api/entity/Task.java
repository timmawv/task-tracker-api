package avlyakulov.timur.task_tracker_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private Boolean isCompleted;

    private Date createdAt;

    private Date finishedAt;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;
}