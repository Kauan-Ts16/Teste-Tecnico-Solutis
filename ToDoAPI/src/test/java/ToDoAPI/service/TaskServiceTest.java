package ToDoAPI.service;

import ToDoAPI.enums.StatusEnum;
import ToDoAPI.exception.ExceptionGeneric;
import ToDoAPI.util.TaskUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Test
    @DisplayName("Should save task with correct title and description")
    public void saveCase1() {

        var expected = service.save(TaskUtil.factoryDto());

        assertAll(
                () -> assertEquals(TaskUtil.TITLE, expected.getTitle()),
                () -> assertEquals(TaskUtil.DESCRIPTION, expected.getDescription())
        );
    }

    @Test
    @DisplayName("Should save task with title correct and description null")
    public void saveCase2() {

        var expected = service.save(TaskUtil.factoryDto(TaskUtil.TITLE, null));

        assertAll(
                () -> assertEquals(TaskUtil.TITLE, expected.getTitle()),
                () -> assertNotEquals(TaskUtil.DESCRIPTION, expected.getDescription())
        );
    }

    @Test
    @DisplayName("Should fail when title or description are incorrect after saving")
    public void saveCase3() {

        var expected = service.save(TaskUtil.factoryDto(TaskUtil.TITLE_ALTERNATIVE, TaskUtil.DESCRIPTION_ALTERNATIVE));

        assertAll(
                () -> assertNotEquals(TaskUtil.TITLE, expected.getTitle()),
                () -> assertNotEquals(TaskUtil.DESCRIPTION, expected.getDescription())
        );
    }

    @Test
    @DisplayName("Should update a task successfully when valid data is provided")
    public void updateCase1() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var updatedTask = service.update(TaskUtil.factoryPutDto(taskId, TaskUtil.TITLE_ALTERNATIVE, TaskUtil.DESCRIPTION_ALTERNATIVE));

        assertAll(
                () -> assertNotNull(updatedTask),
                () -> assertEquals(taskId, updatedTask.getId()),
                () -> assertEquals(TaskUtil.TITLE_ALTERNATIVE, updatedTask.getTitle()),
                () -> assertEquals(TaskUtil.DESCRIPTION_ALTERNATIVE, updatedTask.getDescription())
        );
    }

    @Test
    @DisplayName("Should update a task successfully even with null description")
    public void updateCase2() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var updatedTask = service.update(TaskUtil.factoryPutDto(taskId, TaskUtil.TITLE_ALTERNATIVE, null));

        assertAll(
                () -> assertNotNull(updatedTask),
                () -> assertEquals(taskId, updatedTask.getId()),
                () -> assertEquals(TaskUtil.TITLE_ALTERNATIVE, updatedTask.getTitle()),
                () -> assertEquals(TaskUtil.DESCRIPTION, updatedTask.getDescription())
        );
    }

    @Test
    @DisplayName("Should update a task successfully even with empty description")
    public void updateCase3() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var updatedTask = service.update(TaskUtil.factoryPutDto(taskId, TaskUtil.TITLE_ALTERNATIVE, " "));

        assertAll(
                () -> assertNotNull(updatedTask),
                () -> assertEquals(taskId, updatedTask.getId()),
                () -> assertEquals(TaskUtil.TITLE_ALTERNATIVE, updatedTask.getTitle()),
                () -> assertNotEquals(TaskUtil.DESCRIPTION, updatedTask.getDescription())
        );
    }

    @Test
    @DisplayName("Should throw exception when updating a task with title null")
    public void updateCase4() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var invalidTaskDto = TaskUtil.factoryPutDto(taskId, null, null);

        assertThrows(ExceptionGeneric.class, () -> service.update(invalidTaskDto));
    }

    @Test
    @DisplayName("Should throw exception when updating a task with invalid data")
    public void updateCase5() {

        var invalidTaskDto = TaskUtil.factoryPutDto(UUID.randomUUID(), "", null);

        assertThrows(ExceptionGeneric.class, () -> service.update(invalidTaskDto));
    }

    @Test
    @DisplayName("Should throw an exception when updating a task with invalid id and null title")
    public void updateCase6() {

        var invalidTaskDto = TaskUtil.factoryPutDto(UUID.randomUUID(), null, null);

        assertThrows(ExceptionGeneric.class, () -> service.update(invalidTaskDto));
    }

    @Test
    @DisplayName("Should update the status of a task successfully when valid data is provided")
    public void updateStatusCase1() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var updatedTask = service.updateStatus(TaskUtil.factoryPutStatusDto(taskId, StatusEnum.COMPLETED.name()));

        assertAll(
                () -> assertNotNull(updatedTask),
                () -> assertEquals(taskId, updatedTask.getId()),
                () -> assertEquals(StatusEnum.COMPLETED, updatedTask.getStatus())
        );
    }

    @Test
    @DisplayName("Should throw an exception when updating the status of a task with invalid data")
    public void updateStatusCase2() {

        var invalidTaskDto = TaskUtil.factoryPutStatusDto(UUID.randomUUID(), null);

        assertThrows(ExceptionGeneric.class, () -> service.updateStatus(invalidTaskDto));

    }

    @Test
    @DisplayName("Should throw an exception when updating a task with an invalid status string")
    public void updateStatusCase3() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var invalidTask = TaskUtil.factoryPutStatusDto(taskId, "INVALID");

        assertThrows(Exception.class, () -> service.updateStatus(invalidTask));

    }

    @Test
    @DisplayName("Should delete task successfully and throw exception when searching for deleted task")
    public void deleteCase1() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        service.delete(taskId);

        assertThrows(ExceptionGeneric.class, () -> service.findById(taskId));

    }

    @Test
    @DisplayName("Should throw exception when trying to delete a non-existent task")
    public void deleteCase2() {

        var fakeId = UUID.randomUUID();

        assertAll(
                () -> assertThrows(ExceptionGeneric.class, () -> service.findById(fakeId)),
                () -> assertThrows(ExceptionGeneric.class, () -> service.delete(fakeId))
        );

    }

    @Test
    @DisplayName("Should return task when searching with a valid ID")
    public void findByIdCase1() {

        var task = service.save(TaskUtil.factoryDto());
        var taskId = task.getId();

        var foundTask = service.findById(taskId);

        assertAll(
                () -> assertNotNull(foundTask),
                () -> assertEquals(taskId, foundTask.getId()),
                () -> assertEquals(TaskUtil.TITLE, foundTask.getTitle()),
                () -> assertEquals(TaskUtil.DESCRIPTION, foundTask.getDescription())
        );

    }

    @Test
    @DisplayName("Should throw exception when searching for a non-existent task")
    public void findByIdCase2() {

        var fakeId = UUID.randomUUID();

        assertThrows(ExceptionGeneric.class, () -> service.findById(fakeId));

    }

    @Test
    @DisplayName("Should return a list of tasks when there are tasks in the database")
    public void findAllCase1() {

        var task1 = service.save(TaskUtil.factoryDto());
        var task2 = service.save(TaskUtil.factoryDto(TaskUtil.TITLE_ALTERNATIVE, TaskUtil.DESCRIPTION_ALTERNATIVE));

        var taskList = service.findAll();

        assertAll(
                () -> assertNotNull(taskList),
                () -> assertFalse(taskList.isEmpty()),
                () -> assertTrue(taskList.contains(task1)),
                () -> assertTrue(taskList.contains(task2))
        );

    }

    @Test
    @DisplayName("Should return an empty list when there are no tasks")
    public void findAllCase2() {

        service.deleteAll();
        var taskList = service.findAll();

        assertAll(
                () -> assertNotNull(taskList),
                () -> assertTrue(taskList.isEmpty())
        );
    }

}
