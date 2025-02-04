package ToDoAPI.validation.component;

import ToDoAPI.exception.ExceptionGeneric;
import ToDoAPI.respository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskValidation {
    private final TaskRepository repository;

    public boolean isValid(UUID value) {
        if (!existsById(value))
            throw new ExceptionGeneric("Task not exists!", "Task with id: "+ value +" not exists.", 400);
        return true;
    }

    private boolean existsById(UUID id) {
        return repository.existsById(id);
    }

}
