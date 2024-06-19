package com.pds.biblioteca.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Map;

@Data
public abstract class AbstractFirestoreEntity {
    private String id;

    public Map<String, Object> toMap2() {
        final ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(this, new TypeReference<Map<String, Object>>() {
        });
        map.remove("id");
        return map;
    }
}
