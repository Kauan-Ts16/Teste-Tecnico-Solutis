package ToDoAPI.validation.component;

public class NotNull {

    public static boolean isValid(Object value){
        return value != null;
    }
}