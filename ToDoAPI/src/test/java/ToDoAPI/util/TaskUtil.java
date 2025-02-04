package ToDoAPI.util;

import ToDoAPI.dto.TaskPostDto;
import ToDoAPI.dto.TaskPutDto;
import ToDoAPI.dto.TaskPutStatusDto;
import ToDoAPI.enums.StatusEnum;
import ToDoAPI.model.TaskModel;

import java.util.UUID;

public class TaskUtil {
    public static final String TITLE = "TEST", TITLE_ALTERNATIVE = "TESTED", DESCRIPTION = "DESC", DESCRIPTION_ALTERNATIVE = "DESCRIPTION",
            STATUS_PENDING = "PENDING", STATUS_IN_PROGRESS = "IN_PROGRESS", STATUS_COMPLETED = "COMPLETED";

    public static TaskModel factoryModel() {
        return TaskModel
                .builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .status(StatusEnum.valueOf(STATUS_PENDING))
                .build();
    }

    public static TaskModel factoryModel(String title, String description, String status) {
        return TaskModel
                .builder()
                .title(title)
                .description(description)
                .status(StatusEnum.valueOf(status))
                .build();
    }

    public static TaskPostDto factoryDto() {
        return TaskPostDto
                .builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .build();
    }

    public static TaskPostDto factoryDto(String title, String description) {
        return TaskPostDto
                .builder()
                .title(title)
                .description(description)
                .build();
    }

    public static TaskPutDto factoryPutDto(UUID id, String title, String description) {
        return TaskPutDto
                .builder()
                .id(id)
                .title(title)
                .description(description)
                .build();
    }

    public static TaskPutStatusDto factoryPutStatusDto(UUID id, String status) {
        return TaskPutStatusDto
                .builder()
                .id(id)
                .status(status)
                .build();
    }
}
