package com.Test.Tivibu.serializer;

import com.Test.Tivibu.model.Result;
import com.Test.Tivibu.model.users.Tester;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class TesterSerializer extends JsonSerializer<Tester> {
    @Override
    public void serialize(Tester tester, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (tester == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            gen.writeNumberField("tester_id", tester.getTester_id());
            gen.writeStringField("name", tester.getName());
            gen.writeStringField("username", tester.getUsername());
            gen.writeStringField("password", tester.getPassword());
            gen.writeBooleanField("accountNonExpired", tester.isAccountNonExpired());
            gen.writeBooleanField("isEnabled", tester.isEnabled());
            gen.writeBooleanField("accountNonLocked", tester.isAccountNonLocked());
            gen.writeBooleanField("credentialsNonExpired", tester.isCredentialsNonExpired());
            gen.writeStringField("role", tester.getRole().toString());
            gen.writeEndObject();
        }
    }
}
