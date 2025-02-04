package ToDoAPI.validation.component;

public class NotEmpty {

    public static boolean isValid(String value){
        if (value != null)
                return !value.isEmpty();
        return false;
    }
}
