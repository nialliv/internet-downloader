package ru.artemev.internetdownloader.dto.ranobelib;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModelType {

    CHAPTER("chapter");

    private final String value;

    @JsonValue
    public String toValue() {
        return value;
    }
}
