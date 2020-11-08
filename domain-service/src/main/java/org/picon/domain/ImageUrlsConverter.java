package org.picon.domain;

import org.springframework.lang.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ImageUrlsConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull List<String> attribute) {
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(@NonNull String dbData) {
        return Arrays.asList(dbData.split(","));
    }
}
