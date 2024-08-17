package com.dw.ventas.exception.handler;

import com.dw.ventas.exception.AbstractServiceException;
import com.dw.ventas.exception.ErrorMessage;
import com.dw.ventas.exception.impl.*;
import com.dw.ventas.exception.impl.IllegalStateException;
import com.dw.ventas.exception.resource.ErrorItemResource;
import com.dw.ventas.exception.resource.ErrorMessageResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String RUNTIME_EXCEPTION_CODE = "025-1000";
    private static final String RUNTIME_EXCEPTION_MESSAGE_CODE = "global.error.unknown";

    private static final String ACCESS_DENIED_EXCEPTION_CODE = "025-1100";
    private static final String ACCESS_DENIED_EXCEPTION_MESSAGE_CODE = "global.error.access-denied";

    private static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_CODE = "025-1200";
    private static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE_CODE = "global.error.invalid-format";

    private static final String MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_CODE = "025-2200";
    private static final String MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_MESSAGE_CODE = "global.error.file.max-upload-size-exceeded";
    private static final String SIZE_LIMIT_EXCEEDED_EXCEPTION_MESSAGE_CODE = "global.error.size-limit-exceeded";
    private static final String UNKNOWN_MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_MESSAGE_CODE = "global.error.unknown-max-upload-size-exceeded";

    private final MessageSource messageSource;
    private final String maxFileSize;

    public ServiceExceptionHandler(final MessageSource messageSource,
                                   @Value("${spring.servlet.multipart.max-file-size:1}") final String maxFileSize) {
        this.messageSource = messageSource;
        this.maxFileSize = maxFileSize;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMessageResource> handleRuntimeException(final RuntimeException exception) {
        log.error("Unhandled RuntimeException has been thrown.", exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(RUNTIME_EXCEPTION_CODE,
                RUNTIME_EXCEPTION_MESSAGE_CODE, null);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<ErrorMessageResource> handleAuthorizationException(final AbstractServiceException exception) {
        log.debug("AuthorizationException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);
        return ResponseEntity
                .status(FORBIDDEN)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({ConfigurationException.class, InternalInconsistencyException.class, InternalServiceException.class, ExternalServiceException.class})
    public ResponseEntity<ErrorMessageResource> handleInternalServerError(final AbstractServiceException exception) {
        log.error("Internal server error has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorMessageResource> handleBadRequestException(final AbstractServiceException exception) {
        log.debug("BadRequestException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorMessageResource> handleAccessDeniedException(final AccessDeniedException exception) {
        log.debug("AccessDeniedException has been thrown.", exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(ACCESS_DENIED_EXCEPTION_CODE,
                ACCESS_DENIED_EXCEPTION_MESSAGE_CODE, null);
        return ResponseEntity
                .status(FORBIDDEN)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorMessageResource> handleValidationExceptions(final ValidationException exception) {
        log.debug("ValidationException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResourceWithItems(exception);
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessageResource> handleResourceNotFoundExceptions(final ResourceNotFoundException exception) {
        log.debug("ResourceNotFoundException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({ResourceNotFoundPdfException.class})
    public ResponseEntity handleResourceNotFoundPdfExceptions(final ResourceNotFoundPdfException exception) {
        log.debug("ResourceNotFoundPdfException has been thrown. " + exception, exception);

        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler({PreconditionRequiredException.class})
    public ResponseEntity handlePreconditionRequiredExceptions(final PreconditionRequiredException exception) {
        log.debug("PreconditionRequiredException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);
        return ResponseEntity
                .status(PRECONDITION_REQUIRED)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({com.dw.ventas.exception.impl.IllegalStateException.class})
    public ResponseEntity<ErrorMessageResource> handleIllegalStateExceptions(final IllegalStateException exception) {
        log.debug("IllegalStateException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(exception);
        return ResponseEntity
                .status(CONFLICT)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<ErrorMessageResource> handleMaxUploadSizeExceededExceptions(final MaxUploadSizeExceededException exception) {
        log.debug("MaxUploadSizeExceededException has been thrown. " + exception, exception);

        final ErrorMessageResource errorMessageResource;
        final Throwable cause = exception.getCause().getCause();

        if (cause instanceof FileSizeLimitExceededException) {
            errorMessageResource = getErrorMessageResource(MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_CODE,
                    MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_MESSAGE_CODE, new String[]{maxFileSize});
        } else if (cause instanceof SizeLimitExceededException) {
            final SizeLimitExceededException sizeLimitExceededException = (SizeLimitExceededException) cause;
            final Object[] args = {sizeLimitExceededException.getActualSize(), sizeLimitExceededException.getPermittedSize()};

            errorMessageResource = getErrorMessageResource(MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_CODE,
                    SIZE_LIMIT_EXCEEDED_EXCEPTION_MESSAGE_CODE, args);
        } else {
            errorMessageResource = getErrorMessageResource(MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_CODE,
                    UNKNOWN_MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION_MESSAGE_CODE, null);
        }

        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                               final HttpHeaders headers,
                                                               final HttpStatus status,
                                                               final WebRequest request) {
        log.warn("HttpMessageNotReadableException has been thrown.", exception);

        final ErrorMessageResource errorMessageResource = getErrorMessageResource(HTTP_MESSAGE_NOT_READABLE_EXCEPTION_CODE,
                HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE_CODE, null);
        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                               final HttpHeaders headers,
                                                               final HttpStatus status,
                                                               final WebRequest request) {
        log.debug("MethodArgumentNotValidException has been thrown.", exception);

        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        final List<ValidationError> validationErrors = fieldErrors.stream()
                .map(fieldError -> new ValidationError(convertFromCamelCaseToSnakeCase(fieldError.getField()), fieldError.getDefaultMessage()))
                .collect(toList());

        final ValidationException validationException = ValidationException.builder()
                .addValidationErrors(validationErrors)
                .build();
        final ErrorMessageResource errorMessageResource = getErrorMessageResourceWithItems(validationException);

        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .contentType(APPLICATION_JSON_UTF8)
                .body(errorMessageResource);
    }

    private ErrorMessageResource getErrorMessageResource(final AbstractServiceException abstractServiceException) {
        final ErrorMessage errorMessage = abstractServiceException.getErrorMessage();
        return getErrorMessageResource(abstractServiceException.getCode(), errorMessage.getCode(), errorMessage.getArgs());
    }

    private ErrorMessageResource getErrorMessageResource(final String code, final String messageCode, final Object[] args) {
        final String message = messageSource.getMessage(messageCode, args, messageCode, getLocale());

        return ErrorMessageResource.builder()
                .code(code)
                .message(message)
                .build();
    }

    private ErrorMessageResource getErrorMessageResourceWithItems(final ValidationException validationException) {
        final ErrorMessageResource errorMessageResource = getErrorMessageResource(validationException);
        final List<ValidationError> validationErrors = validationException.getValidationErrors();

        if (validationErrors == null || validationErrors.isEmpty()) {
            return errorMessageResource;
        }

        errorMessageResource.setErrors(validationErrors.stream()
                .map(this::getErrorItemResource)
                .collect(toList()));

        return errorMessageResource;
    }

    private ErrorItemResource getErrorItemResource(final ValidationError validationError) {
        final ErrorMessage errorMessage = validationError.getErrorMessage();
        final String messageCode = errorMessage.getCode();
        final String validationMessage = messageSource.getMessage(messageCode, errorMessage.getArgs(), messageCode, getLocale());

        return ErrorItemResource.builder()
                .path(validationError.getPath())
                .message(validationMessage)
                .build();
    }

    private String convertFromCamelCaseToSnakeCase(final String text) {
        return text.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }
}
