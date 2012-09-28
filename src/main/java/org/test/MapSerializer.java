package org.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.Map;

/**
* @author <a href="mailto:ketoth.xupack@gmail.com">ketoth xupack</a>
* @since 9/28/12 3:46 PM
*/
public final class MapSerializer extends ContainerSerializer<Map> {
    public MapSerializer() {
        super(Map.class);
    }

    @Override
    public JavaType getContentType() {
        return SimpleType.construct(Map.class);
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return null;
    }

    @Override
    public boolean isEmpty(final Map value) {
        return value.isEmpty();
    }

    @Override
    public boolean hasSingleElement(final Map value) {
        return value.size() == 1;
    }

    @Override
    protected ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer vts) {
        return null;
    }

    @Override
    public void serialize(final Map x,
                          final JsonGenerator jgen,
                          final SerializerProvider provider) throws IOException {
        jgen.writeStartArray();
        for (final Map.Entry value : ((Map<?, ?>) x).entrySet()) {
            jgen.writeStartObject();
            // TODO: npe
            jgen.writeFieldName("@keyClass");
            jgen.writeString(value.getKey().getClass().getCanonicalName());

            jgen.writeFieldName("@key");
            jgen.writeObject(value.getKey());

            // TODO: npe
            jgen.writeFieldName("@valClass");
            jgen.writeString(value.getValue().getClass().getCanonicalName());

            jgen.writeFieldName("@val");
            jgen.writeObject(value.getValue());

            jgen.getOutputContext().inArray();
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
