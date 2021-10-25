package org.example.app.security;

import lombok.Data;

@Data
public class ContactConfirmationPayload {
    private String code;
    private String contact;
}
