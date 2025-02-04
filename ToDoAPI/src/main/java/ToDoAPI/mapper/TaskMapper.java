package ToDoAPI.mapper;

import ToDoAPI.dto.TaskPostDto;
import ToDoAPI.dto.TaskPutDto;
import ToDoAPI.dto.TaskPutStatusDto;
import ToDoAPI.enums.StatusEnum;
import ToDoAPI.model.TaskModel;
import org.springframework.beans.BeanUtils;

public class TaskMapper {

    public static TaskModel toMapper(TaskPostDto input) {
        TaskModel output = new TaskModel();
        BeanUtils.copyProperties(input, output);
        output.setStatus(StatusEnum.PENDING);

        return output;
    }

    public static TaskModel toMapper(TaskPutDto input, TaskModel output) {
        var description = output.getDescription();
        BeanUtils.copyProperties(input, output);
        if (input.getDescription() == null) output.setDescription(description);

        return output;
    }

    public static TaskModel toMapper(TaskPutStatusDto input, TaskModel output) {
        BeanUtils.copyProperties(input, output);
        output.setStatus(StatusEnum.valueOf(input.getStatus()));

        return output;
    }
}
