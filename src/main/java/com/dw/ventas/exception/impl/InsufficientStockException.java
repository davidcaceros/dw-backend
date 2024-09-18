package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.AbstractServiceException;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class InsufficientStockException extends AbstractServiceException {
    private static final String CODE = "400";
    private final String productName;

    private InsufficientStockException(final String message,
                                       final Throwable cause,
                                       final Map<String, Object> additionalInformation,
                                       final String errorMessageKey,
                                       final Object[] errorMessageArgs,
                                       final String productName) {
        super(message, cause, CODE, additionalInformation, errorMessageKey, errorMessageArgs);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
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
        private String productName;

        private Builder() {
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

        public Builder productName(final String productName) {
            this.productName = productName;
            return this;
        }

        public InsufficientStockException build() {
            return new InsufficientStockException(message, cause, additionalInformation, errorMessageKey, errorMessageArgs, productName);
        }
    }
}
