
package com.example.studentregister.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GeneralControllerAdvice {

    @Value("${exception.path}")
    private String exceptionPath;

    private final LabelMessageAccessor labelMessageAccessor;
    private final BusinessExceptionMessageAccessor businessExceptionMessageAccessor;

    public GeneralControllerAdvice(
            LabelMessageAccessor labelMessageAccessor,
            BusinessExceptionMessageAccessor businessExceptionMessageAccessor) {
        this.labelMessageAccessor = labelMessageAccessor;
        this.businessExceptionMessageAccessor = businessExceptionMessageAccessor;
    }

    private String saveException(Exception exception) {
        String identifier = String.valueOf(System.currentTimeMillis());

        try {
            Date now = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            Path directory = Paths.get(
                    exceptionPath,
                    String.valueOf(year),
                    String.valueOf(month),
                    String.valueOf(day)
            );

            Files.createDirectories(directory);

            Path file = directory.resolve(identifier + ".txt");

            try (BufferedWriter writer = Files.newBufferedWriter(
                    file,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE)) {

                writer.write("message: " + exception.getMessage());
                writer.newLine();
                writer.write("stackTrace:");
                writer.newLine();
                writer.write(ExceptionUtils.getStackTrace(exception));
            }

            return identifier;

        } catch (IOException | RuntimeException e) {
            log.error("Failed to save exception to file", e);
            return identifier;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RunTimeExceptionResponse> handleRunTimeException(
            Exception exception) {

        String identifier = saveException(exception);

        log.error("Unhandled exception. Reference ID: {}", identifier, exception);

        RunTimeExceptionResponse response = new RunTimeExceptionResponse(
                "خطای داخلی سرور رخ داده است. کد پیگیری: " + identifier,
                HttpStatus.INTERNAL_SERVER_ERROR,
                new Date()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiExceptionResponse> handleBusinessException(
            BusinessException exception,
            Locale locale) {

        ApiExceptionResponse response = new ApiExceptionResponse(
                Collections.singletonList(
                        new ApiExceptionResponse.Message(
                                businessExceptionMessageAccessor.getMessage(
                                        LocaleContextHolder.getLocale(),
                                        exception.getErrorMessage().getValue()
                                ),
                                exception.getExtraData()
                        )
                ),
                HttpStatus.CONFLICT,
                new Date()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BusinessExceptionList.class)
    public ResponseEntity<ApiExceptionResponse> handleBusinessExceptionList(
            BusinessExceptionList exceptionList,
            Locale locale) {

        List<ApiExceptionResponse.Message> messages = new ArrayList<>();

        exceptionList.getBusinessExceptionList().forEach((value, key) ->
                messages.add(
                        new ApiExceptionResponse.Message(
                                businessExceptionMessageAccessor.getMessage(
                                        LocaleContextHolder.getLocale(),
                                        value.getErrorMessage().getValue()
                                ),
                                value.getExtraData(),
                                key
                        )
                )
        );

        ApiExceptionResponse response = new ApiExceptionResponse(
                messages,
                HttpStatus.CONFLICT,
                new Date()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse>
    handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            Locale locale) {

        List<FieldError> fieldErrors =
                exception.getBindingResult().getFieldErrors();

        List<String> errorList = new ArrayList<>();

        fieldErrors.forEach(error ->
                errorList.add(
                        labelMessageAccessor.getMessage(
                                LocaleContextHolder.getLocale(),
                                error.getField()
                        ) + " : " + error.getDefaultMessage()
                )
        );

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                new Date(),
                errorList
        );

        log.warn(
                "Validation errors: {}, Parameters: {}",
                errorList,
                exception.getBindingResult().getTarget()
        );

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(response);
    }
}
