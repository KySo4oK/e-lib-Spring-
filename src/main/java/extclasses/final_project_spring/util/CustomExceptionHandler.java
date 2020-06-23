package extclasses.final_project_spring.util;

import extclasses.final_project_spring.exception.IncorrectDataException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({IncorrectDataException.class})
    public final ResponseEntity<String> handleAllExceptions(Exception ex, Locale locale) {
        return new ResponseEntity<>(messageSource.getMessage("exc", null, locale),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}