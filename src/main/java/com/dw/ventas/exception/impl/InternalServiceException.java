package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.AbstractServiceException;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class InternalServiceException extends AbstractServiceException {
    private static final String CODE = "025-0800";

    private InternalServiceException(final String message,
                                     final Throwable cause,
                                     final Map<String, Object> additionalInformation,
                                     final String errorMessageKey,
                                     final Object[] errorMessageArgs) {
        super(message, cause, CODE, additionalInformation, errorMessageKey, errorMessageArgs);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private String errorMessageKey;
        private Object[] errorMessageArgs;
        private Throwable cause;
        private Map<String, Object> additionalInformation;

        private Builder() {
            this.errorMessageKey = "global.error.unknown";
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder cause(final Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder errorMessageKey(final String errorMessageKey) {
            this.errorMessageKey = errorMessageKey;
            return this;
        }

        public Builder errorMessageArgs(final Object... errorMessageArgs) {
            this.errorMessageArgs = errorMessageArgs;
            return this;
        }

        public Builder addAdditionalInformation(final String key, final Object value) {
            if (additionalInformation == null) {
                additionalInformation = new HashMap<>();
            }

            additionalInformation.put(key, value);

            return this;
        }

        public InternalServiceException build() {
            return new InternalServiceException(message, cause, additionalInformation, errorMessageKey, errorMessageArgs);
        }
    }
}