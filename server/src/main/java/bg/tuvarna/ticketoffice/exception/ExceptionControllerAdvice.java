package bg.tuvarna.ticketoffice.exception;

import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public ExceptionControllerAdvice(ResourceBundleUtil resourceBundleUtil) {
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @ExceptionHandler(value = { BadCredentialsException.class, UsernameNotFoundException.class })
    protected ResponseEntity<CommonMessageResponse> handleConflict(RuntimeException ex) {
        String message = resourceBundleUtil.getMessage("userLogin.badCredentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new CommonMessageResponse(message));
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<CommonMessageResponse> handleInvalidData(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonMessageResponse(ex.getMessage()));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
