package com.dw.ventas.exception.impl;

import com.dw.ventas.exception.AbstractServiceException;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MultipleInsufficientStockException extends AbstractServiceException {
    private static final String CODE = "400";
    private final List<InsufficientStockException> insufficientStockExceptions;

    public MultipleInsufficientStockException(List<InsufficientStockException> insufficientStockExceptions) {
        super("Hay múltiples productos con stock insuficiente", null, CODE, null, null, null);
        this.insufficientStockExceptions = insufficientStockExceptions;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return super.getAdditionalInformation();
    }
}