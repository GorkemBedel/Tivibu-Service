package com.Test.Tivibu.serializer;

import com.Test.Tivibu.model.Result;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class ResultSerializer extends JsonSerializer<Result> {
    @Override
    public void serialize(Result value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            gen.writeNumberField("id", value.getId());
            gen.writeBooleanField("isOk", value.getIsOk());
            gen.writeStringField("comment", value.getComment());

            // reportPhoto alanını ekleyin
            if (value.getReportPhoto() != null) {
                String base64Photo = Base64.getEncoder().encodeToString(value.getReportPhoto());
                gen.writeStringField("reportPhoto", base64Photo);
            } else {
                gen.writeNullField("reportPhoto");
            }
            gen.writeEndObject();
        }
    }
}

