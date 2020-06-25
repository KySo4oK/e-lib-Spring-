package extclasses.final_project_spring.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class SuccessResponseEntity {
    private final MessageSource messageSource;

    public SuccessResponseEntity(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResponseEntity<Map<String, String>> getSuccessResponseEntityWithMessage(String message) {
        return ResponseEntity.ok().body(
                Collections.singletonMap("message",
                        messageSource.getMessage(message, null, LocaleContextHolder.getLocale())));
    }
}
