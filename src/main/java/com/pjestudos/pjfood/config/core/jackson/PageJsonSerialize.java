package com.pjestudos.pjfood.config.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

//Responsavel por customizar a representação da paginação se eu quiser usar
//@JsonComponent
public class PageJsonSerialize extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page<?> page, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("content", page.getContent());
        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
        jsonGenerator.writeNumberField("number", page.getNumber());
        jsonGenerator.writeEndObject();

    }
}
