package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.AbstractServiceException;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class PreconditionRequiredException extends AbstractServiceException {
    private static final String CODE = "025-2100";

    private PreconditionRequiredException(final String exceptionMessage,
                                          final Throwable cause,
                                          final Map<String, Object> additionalInformation,
                                          final String errorMessagePropertyKey,
                                          final Object[] errorMessagePropertyArgs) {
        super(exceptionMessage, cause, CODE, additionalInformation, errorMessagePropertyKey, errorMessagePropertyArgs);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String exceptionMessage;
        private String errorMessagePropertyKey;
        private Object[] errorMessagePropertyArgs;
        private Throwable cause;
        private Map<String, Object> additionalInformation;

        private Builder() {
        }

        public Builder exceptionMessage(final String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
            return this;
        }

        public Builder cause(final Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder displayMessage(final String errorMessagePropertyKey, final Object... errorMessagePropertyArgs) {
            this.errorMessagePropertyKey = errorMessagePropertyKey;
            this.errorMessagePropertyArgs = errorMessagePropertyArgs;
            return this;
        }

        public Builder addAdditionalInformation(final String key, final Object value) {
            if (additionalInformation == null) {
                additionalInformation = new HashMap<>();
            }

            additionalInformation.put(key, value);

            return this;
        }

        public PreconditionRequiredException build() {
            return new PreconditionRequiredException(exceptionMessage, cause, additionalInformation, errorMessagePropertyKey, errorMessagePropertyArgs);
        }
    }
}
