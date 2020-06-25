package extclasses.final_project_spring.util;

import extclasses.final_project_spring.exception.CustomException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({CustomException.class})
    public final ResponseEntity<Map<String, String>> handleCustomExceptions(Exception ex, Locale locale) {
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("error",
                        messageSource.getMessage(ex.getMessage(), null, locale)));
    }
}