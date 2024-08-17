package avlyakulov.timur.task_tracker_api.repository;

import avlyakulov.timur.task_tracker_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findAllByOwnerId(Integer userId);

    @Query("select t from Task t where t.id = ?1 and t.owner.id = ?2")
    Optional<Task> findTaskByIdAndUserId(String taskId, Integer userId);
}