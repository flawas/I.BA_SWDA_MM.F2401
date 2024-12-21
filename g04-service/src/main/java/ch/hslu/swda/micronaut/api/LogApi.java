package ch.hslu.swda.micronaut.api;

import ch.hslu.swda.micronaut.controller.LogController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.*;

public class LogApi {
    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);
    private List<String> pseudoLogs = new ArrayList<>();
    private Map<UUID, String> logsWithId = new HashMap<>();


    public LogApi() {
        //Create Pseudo Logs
        for (int i = 0; i < 100; i++){
            pseudoLogs.add("Info: Customer order received.");
            pseudoLogs.add("Info: Customer with id: 143 has payed order.");
            pseudoLogs.add("Warning: No answer received from system: ABC.");
        }
        for(String log:pseudoLogs){
            logsWithId.put(UUID.randomUUID(),log);

        }
    }

    public String getSpecificLog(final UUID id){
        if(!logsWithId.containsKey(id)) {
            LOG.error("LOG {} not fount", id);
            return "LOG not found";
        } else {
            LOG.info("API: log shown {}", id);
            Map<String, String> logDetails = new HashMap<>();
            logDetails.put("ID", String.valueOf(id));
            logDetails.put("Log", logsWithId.get(id));
            return getMapToJson(logDetails);
        }
    }

    public String getAllLogs() {
        return getMapToJson(logsWithId);
    }

    private String getMapToJson(final Map inputMap) {
        Gson gson = new Gson();
        Type typeObject = new TypeToken<Map>() {}.getType();
        return gson.toJson(inputMap, typeObject);
    }

}
