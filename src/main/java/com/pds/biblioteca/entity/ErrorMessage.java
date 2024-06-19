package com.pds.biblioteca.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {
    private String error;
    private String message;
}
