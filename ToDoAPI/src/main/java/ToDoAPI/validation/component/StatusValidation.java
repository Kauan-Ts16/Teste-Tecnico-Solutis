package ToDoAPI.validation.component;

import ToDoAPI.enums.StatusEnum;
import ToDoAPI.exception.ExceptionGeneric;

public class StatusValidation {

    public static boolean enumValidate(String value) {
        try {
            if (value != null) {
                StatusEnum status = StatusEnum.valueOf(value);
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            throw new ExceptionGeneric("Status invalid!", "Status must be PENDING, IN_PROGRESS OR COMPLETED", 400);
        }
    }
}
