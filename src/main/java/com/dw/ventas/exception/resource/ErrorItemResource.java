package com.dw.ventas.exception.resource;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorItemResource {
    private String path;
    private String message;
}
