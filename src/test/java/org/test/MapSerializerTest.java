package org.test;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.AUTO_DETECT_GETTERS;
import static com.fasterxml.jackson.databind.MapperFeature.AUTO_DETECT_SETTERS;

/**
 * @author <a href="mailto:ketoth.xupack@gmail.com">ketoth xupack</a>
 * @since 9/28/12 3:44 PM
 */
public class MapSerializerTest {
    public static ObjectMapper mapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(AUTO_DETECT_GETTERS, false);
        mapper.configure(AUTO_DETECT_SETTERS, false);
        mapper.setSerializationInclusion(NON_NULL);
        mapper.setVisibilityChecker(
                VisibilityChecker.Std.defaultInstance().withFieldVisibility(ANY)
        );

        final SimpleModule module = new SimpleModule("map",
                Version.unknownVersion());
        module.addSerializer(new MapSerializer());
        mapper.registerModule(module);

        return mapper;
    }

    @Test
    public void mapSerialize() throws IOException {
        final ObjectMapper mapper = mapper();
        final Map<Integer, Object> map = new HashMap<Integer, Object>();
        final Map<Integer, Object> map2 = new HashMap<Integer, Object>();
        map2.put(2, 3);
        map.put(1, map2);

        // at this line valid json produced
        System.err.println(map + " -> " + mapper.writeValueAsString(map));

        // however with pretty printing enabled the same operation fails with
        // java.lang.ArrayIndexOutOfBoundsException
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        System.err.println(map + " -> " + mapper.writeValueAsString(map));
    }
}
