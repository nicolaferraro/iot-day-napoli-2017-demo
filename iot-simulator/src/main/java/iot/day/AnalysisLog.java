package iot.day;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class AnalysisLog {

    private static final int LOG_MAX_SIZE = 5;
    private Map<String, List<String>> data = new TreeMap<>();

    public AnalysisLog() {
    }

    public synchronized void add(@Header("group") String group, String str) {
        if (!data.containsKey(group)) {
            data.put(group, new LinkedList<>());
        }

        List<String> log = data.get(group);
        log.add(str);
        if (log.size() > LOG_MAX_SIZE) {
            log.remove(0);
        }
    }

    public synchronized List<String> get(@Header("group") String group) {
        return new LinkedList<>(data.get(group));
    }

}
