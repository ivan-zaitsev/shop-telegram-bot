package ua.ivan909020.admin.utils;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public final class ControllerUtils {

    private ControllerUtils() {
    }

    public static Map<String, String> findErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

}
