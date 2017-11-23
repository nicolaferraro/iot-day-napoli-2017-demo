package iot.day;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Component
public class AnalysisLog {

    private static final int LOG_MAX_SIZE = 5;
    private Map<String, List<String>> data = new TreeMap<>();
    private Map<String, Integer> counters = new TreeMap<>();

    public AnalysisLog() {
        // static demo data
//        add("avg", "12.3");
//        add("avg", "12.4");
//        add("avg", "12.5");
//        add("avg", "12.6");
//        add("avg", "12.7");
//        add("avg", "12.8");
//        add("avg", "12.8");
//        add("avg", "12.8");
//
//
//        add("avgf", "1.4");
//        add("avgf", "1.5");
//        add("avgf", "1.6");
//
//        add("action", "Hello!!!");
    }

    public synchronized void add(@Header("group") String group, String str) {
        if (!data.containsKey(group)) {
            data.put(group, new LinkedList<>());
            counters.put(group, 0);
        }

        List<String> log = data.get(group);
        int counter = counters.get(group);

        counters.put(group, counter + 1);

        log.add(counter + ") " + str);
        if (log.size() > LOG_MAX_SIZE) {
            log.remove(0);
        }
    }

    public synchronized List<String> get(@Header("group") String group) {
        return new ArrayList<>(Optional.ofNullable(data.get(group)).orElse(Collections.emptyList()));
    }

}
