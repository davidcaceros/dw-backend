package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.AbstractServiceException;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
public class ValidationException extends AbstractServiceException {
    private static final String CODE = "025-0500";
    private static final String ERROR_MESSAGE_CODE = "global.error.validation-exception-message";

    @Getter
    private final List<ValidationError> validationErrors;

    public ValidationException(final String code,
                               final String message,
                               final Throwable cause,
                               final Map<String, Object> additionalInformation,
                               final List<ValidationError> validationErrors) {
        super(message, cause, code, additionalInformation, ERROR_MESSAGE_CODE, null);
        this.validationErrors = validationErrors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String message;
        private List<ValidationError> validationErrors;
        private Map<String, Object> additionalInformation;

        private Builder() {
        }

        public Builder code(final String code) {
            this.code = code;
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder addAdditionalInformation(final String key, final Object value) {
            if (additionalInformation == null) {
                additionalInformation = new HashMap<>();
            }

            additionalInformation.put(key, value);

            return this;
        }

        public Builder addValidationErrors(final List<ValidationError> validationErrors) {
            if (this.validationErrors == null) {
                this.validationErrors = new ArrayList<>();
            }

            this.validationErrors.addAll(validationErrors);
            return this;
        }

        public Builder addValidationError(final String path, final String errorMessageCode, final Object... args) {
            if (validationErrors == null) {
                validationErrors = new ArrayList<>();
            }

            final ValidationError validationError = new ValidationError(path, errorMessageCode, args);
            validationErrors.add(validationError);
            return this;
        }

        public ValidationException build() {
            if (this.code == null) {
                this.code = CODE;
            }

            return new ValidationException(code, message, null, additionalInformation, validationErrors);
        }
    }
}
