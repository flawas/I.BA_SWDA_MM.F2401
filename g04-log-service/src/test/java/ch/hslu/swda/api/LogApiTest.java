//package ch.hslu.swda.micronaut.api;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.google.gson.Gson;
//import io.micronaut.http.HttpRequest;
//import io.micronaut.http.client.HttpClient;
//import io.micronaut.http.client.annotation.Client;
//import io.micronaut.runtime.server.EmbeddedServer;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.shaded.com.google.common.reflect.TypeToken;
//
//import java.util.Map;
//import java.util.regex.Pattern;
//
//@MicronautTest
//class LogApiTest {
//    @Inject
//    EmbeddedServer server;
//
//    @Inject
//    @Client("/")
//    HttpClient client;
//
//    @Test
//    void testGetAllLogs(){
//        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/log"));
//        Map<String, String> logs = new Gson().fromJson(response, new TypeToken<Map<String, String>>(){}.getType());
//
//        Pattern pattern = Pattern.compile("\"[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}\": \".*?\"");
//
//        for (Map.Entry<String, String> entry : logs.entrySet()) {
//            String logEntry = "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"";
//            assertTrue(pattern.matcher(logEntry).matches());
//        }
//    }
//
//    @Test
//    void testGetSpecificLog(){
//        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/log"));
//        Map<String, String> logs = new Gson().fromJson(response, new TypeToken<Map<String, String>>(){}.getType());
//        String firstUUID = logs.keySet().iterator().next();
//        String specificLogResponse = client.toBlocking().retrieve(HttpRequest.GET("/api/log/" + firstUUID));
//        Map<String, String> specificLog = new Gson().fromJson(specificLogResponse, new TypeToken<Map<String, String>>(){}.getType());
//        assertEquals(firstUUID, specificLog.get("ID"));
//        assertEquals(logs.get(firstUUID), specificLog.get("Log"));
//    }
//}
