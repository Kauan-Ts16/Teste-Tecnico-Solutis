package ToDoAPI.model;

import ToDoAPI.enums.StatusEnum;
import ToDoAPI.util.TaskUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskModelTest {
    @Test
    public void construct() {
        var expected = TaskUtil.factoryModel();

        assertAll(
                () -> assertNull(new TaskModel().getTitle()),
                () -> assertEquals(expected.getTitle(), TaskUtil.TITLE),
                () -> assertEquals(expected.getDescription(), TaskUtil.DESCRIPTION),
                () -> assertEquals(expected.getStatus().name(), TaskUtil.STATUS_PENDING)
        );
    }

    @Test
    public void update() {
        var expected = TaskUtil.factoryModel();

        expected.setTitle(TaskUtil.TITLE_ALTERNATIVE);
        expected.setDescription(TaskUtil.DESCRIPTION_ALTERNATIVE);
        expected.setStatus(StatusEnum.valueOf(TaskUtil.STATUS_IN_PROGRESS));

        assertAll(
                () -> assertEquals(expected.getTitle(), TaskUtil.TITLE_ALTERNATIVE),
                () -> assertEquals(expected.getDescription(), TaskUtil.DESCRIPTION_ALTERNATIVE),
                () -> assertEquals(expected.getStatus().name(), TaskUtil.STATUS_IN_PROGRESS),

                () -> assertNotEquals(expected.getTitle(), TaskUtil.TITLE),
                () -> assertNotEquals(expected.getDescription(), TaskUtil.DESCRIPTION),
                () -> assertNotEquals(expected.getStatus().name(), TaskUtil.STATUS_PENDING)
        );
    }

    @Test
    public void updateAlternative() {
        var expected = TaskUtil.factoryModel();

        expected.setTitle(TaskUtil.TITLE_ALTERNATIVE);
        expected.setDescription(TaskUtil.DESCRIPTION_ALTERNATIVE);
        expected.setStatus(StatusEnum.valueOf(TaskUtil.STATUS_COMPLETED));

        assertAll(
                () -> assertEquals(expected.getTitle(), TaskUtil.TITLE_ALTERNATIVE),
                () -> assertEquals(expected.getDescription(), TaskUtil.DESCRIPTION_ALTERNATIVE),
                () -> assertEquals(expected.getStatus().name(), TaskUtil.STATUS_COMPLETED),

                () -> assertNotEquals(expected.getTitle(), TaskUtil.TITLE),
                () -> assertNotEquals(expected.getDescription(), TaskUtil.DESCRIPTION),
                () -> assertNotEquals(expected.getStatus().name(), TaskUtil.STATUS_PENDING)
        );
    }
}
