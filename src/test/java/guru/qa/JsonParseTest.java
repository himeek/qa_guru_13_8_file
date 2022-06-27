package guru.qa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParseTest {

    ClassLoader classLoader = JsonParseTest.class.getClassLoader();

    @Test
    void jsonJson() throws Exception {
        InputStream inputStream = classLoader.getResourceAsStream("example.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream));

        assertThat(jsonNode.withArray("students").findValue("gender").asText()).isEqualTo("female");
        assertThat(jsonNode.withArray("students").findValue("age").asInt()).isEqualTo(19);
        assertThat(jsonNode.withArray("students").findValue("grant").asBoolean()).isEqualTo(true);
        assertThat(jsonNode.withArray("students").findValue("name").asText()).isEqualTo("Piter");
        assertThat(jsonNode.withArray("students").findValue("country").asText()).isEqualTo("Russia");
    }
}

