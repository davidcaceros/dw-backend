package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.ErrorMessage;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidationError {
    private String path;
    private ErrorMessage errorMessage;
    private Map<String, Object> metadata;

    public ValidationError(String path, String errorMessageCode, Object... args) {
        this.path = path;
        this.errorMessage = new ErrorMessage(errorMessageCode, args);
    }

    private ValidationError(String path, Map<String, Object> metadata, String errorMessageCode, Object... args) {
        this.path = path;
        this.errorMessage = new ErrorMessage(errorMessageCode, args);
        this.metadata = metadata;
    }

    public void setMetadataValue(final String key, final Object value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }

        metadata.put(key, value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String path;
        private String errorMessagePropertyKey;
        private Object[] errorMessagePropertyArgs;
        private Map<String, Object> metadata;

        private Builder() {
        }

        public Builder path(final String path) {
            this.path = path;
            return this;
        }

        public Builder errorMessagePropertyKey(final String errorMessagePropertyKey) {
            this.errorMessagePropertyKey = errorMessagePropertyKey;
            return this;
        }

        public Builder errorMessagePropertyArgs(final Object... errorMessagePropertyArgs) {
            this.errorMessagePropertyArgs = errorMessagePropertyArgs;
            return this;
        }

        public Builder metadata(final String key, final Object value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }

            metadata.put(key, value);

            return this;
        }

        public ValidationError build() {
            return new ValidationError(path, metadata, errorMessagePropertyKey, errorMessagePropertyArgs);
        }
    }

}
