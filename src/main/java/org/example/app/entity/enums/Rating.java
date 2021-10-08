package org.example.app.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Rating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    public static Rating of(int rate) {
        return Stream.of(Rating.values())
                .filter(r -> r.getValue() == rate)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
