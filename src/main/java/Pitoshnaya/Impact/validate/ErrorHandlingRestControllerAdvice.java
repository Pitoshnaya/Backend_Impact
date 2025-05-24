package Pitoshnaya.Impact.validate;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingRestControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
        ConstraintViolationException e
    ) {
        final List<Map<String, String>> violations = e.getConstraintViolations().stream()
            .map(
                violation -> Map.of(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
                )
            ).toList();

        return new ValidationErrorResponse(violations);
    }

}
