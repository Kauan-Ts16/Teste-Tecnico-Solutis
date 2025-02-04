package ToDoAPI.service;

import ToDoAPI.dto.TaskPostDto;
import ToDoAPI.dto.TaskPutDto;
import ToDoAPI.dto.TaskPutStatusDto;
import ToDoAPI.exception.ExceptionGeneric;
import ToDoAPI.mapper.TaskMapper;
import ToDoAPI.model.TaskModel;
import ToDoAPI.respository.TaskRepository;
import ToDoAPI.validation.component.NotEmpty;
import ToDoAPI.validation.component.NotNull;
import ToDoAPI.validation.component.StatusValidation;
import ToDoAPI.validation.component.TaskValidation;
import ToDoAPI.validation.rule.Validation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TaskService implements Validation<TaskPostDto, TaskPutDto, TaskPutStatusDto> {

    private final TaskRepository taskRepository;
    private final TaskValidation taskValidation;

    @Transactional
    public TaskModel save(TaskPostDto taskPostDto) {
        validatedPost(taskPostDto);

        return taskRepository.save(TaskMapper.toMapper(taskPostDto));
    }

    public TaskModel update(TaskPutDto taskPutDto) {
        validatedPut(taskPutDto);

        return taskRepository.save(TaskMapper.toMapper(taskPutDto, findById(taskPutDto.getId())));
    }

    public TaskModel updateStatus(TaskPutStatusDto taskPutStatusDto) {
        validatedPutStatus(taskPutStatusDto);

        return taskRepository.save(TaskMapper.toMapper(taskPutStatusDto, findById(taskPutStatusDto.getId())));
    }

    public void delete(UUID id) {
        taskValidation.isValid(id);
        taskRepository.deleteById(id);
    }

    public TaskModel findById(UUID id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ExceptionGeneric("Task not found!", "Task with id: "+ id +" not found.", 404)
        );
    }

    public List<TaskModel> findAll() {
        return taskRepository.findAll();
    }

    public boolean validatePost(TaskPostDto value) {
        return Stream.of(
                NotNull.isValid(value.getTitle()),
                NotEmpty.isValid(value.getTitle())

        ).allMatch(valor -> valor.equals(true));
    }

    @Override
    public boolean validatePut(TaskPutDto value) {
        return Stream.of(
                taskValidation.isValid(value.getId()),
                NotNull.isValid(value.getTitle()),
                NotEmpty.isValid(value.getTitle())

        ).allMatch(valor -> valor.equals(true));
    }

    @Override
    public boolean validatePutStatus(TaskPutStatusDto value) {
        return Stream.of(
                taskValidation.isValid(value.getId()),
                NotNull.isValid(value.getStatus()),
                StatusValidation.enumValidate(value.getStatus())

        ).allMatch(valor -> valor.equals(true));
    }

    @Override
    public void validatedPost(TaskPostDto value) {
        if(!validatePost(value))
            throw new ExceptionGeneric("Task invalid!", "Task Post format is invalid.", 400);
    }

    @Override
    public void validatedPut(TaskPutDto value) {
        if(!validatePut(value))
            throw new ExceptionGeneric("Update Task failed!", "Task update format is invalid.", 400);
    }

    @Override
    public void validatedPutStatus(TaskPutStatusDto value) {
        if (!validatePutStatus(value))
            throw new ExceptionGeneric("Update Status failed!", "Status update format is invalid.", 400);
    }

    public void deleteAll() {
        taskRepository.deleteAll();
    }

}
