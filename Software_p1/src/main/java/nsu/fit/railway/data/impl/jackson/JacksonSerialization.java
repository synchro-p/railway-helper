package nsu.fit.railway.data.impl.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JacksonSerialization {

    private final static String JSON_PATH = "";

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void serialize(T object, String path) {
        try {
            objectMapper.writeValue(new File(JSON_PATH + path), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T deserialize(String path, Class<T> className) {
        T object = null;

        try {
            object = objectMapper.readValue(new File(JSON_PATH + path), className);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }
}
