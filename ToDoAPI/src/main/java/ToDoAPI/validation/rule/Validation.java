package ToDoAPI.validation.rule;

public interface Validation<T, E, O> {
    boolean validatePost(T value);
    void validatedPost(T value);
    boolean validatePut(E value);
    void validatedPut(E value);
    boolean validatePutStatus(O value);
    void validatedPutStatus(O value);
}
