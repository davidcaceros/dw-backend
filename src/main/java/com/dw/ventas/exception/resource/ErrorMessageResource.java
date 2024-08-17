package com.dw.ventas.exception.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class ErrorMessageResource {
    private String code;
    private String message;
    private List<ErrorItemResource> errors;
}
