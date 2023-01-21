package com.gb.spring1.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FieldsValidationError {
    private List<String> errorsFieldsMessages;

    public FieldsValidationError(List<String> errorsFieldsMessages) {
        this.errorsFieldsMessages = errorsFieldsMessages;
    }
}
