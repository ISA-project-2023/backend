package ftn.isa.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.isa.dto.EquipmentAmountDTO;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class EquipmentListConverter implements AttributeConverter<List<EquipmentAmountDTO>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<EquipmentAmountDTO> equipment) {
        try {
            return objectMapper.writeValueAsString(equipment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting equipment list to JSON", e);
        }
    }

    @Override
    public List<EquipmentAmountDTO> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new ArrayList<EquipmentAmountDTO>().getClass());
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to equipment list", e);
        }
    }
}
