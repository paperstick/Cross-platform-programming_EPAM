package com.bsuir.lab_1;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomNumberCache {
  private final HashMap<Double, Map<String, Boolean>> cache = new HashMap<>();

    public boolean isContain(Double key) {
        return cache.containsKey(key);
    }

    public void cacheResult(Double key, Map<String, Boolean> result) {
        cache.put(key, result);
    }

    public Map<String, Boolean> getCachedResult(Double key) {
        return cache.get(key);
    }
}
