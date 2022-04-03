package org.example.web.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
public class FileDataDto {
    private String id;
    @NonNull
    private String name;
}
