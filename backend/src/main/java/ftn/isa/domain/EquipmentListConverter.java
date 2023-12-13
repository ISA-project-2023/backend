package ftn.isa.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class EquipmentListConverter implements AttributeConverter<List<Equipment>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Equipment> equipment) {
        try {
            return objectMapper.writeValueAsString(equipment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting equipment list to JSON", e);
        }
    }

    @Override
    public List<Equipment> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new ArrayList<Equipment>().getClass());
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to equipment list", e);
        }
    }
}
