package fr.pandaguerrier.jobs.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class CacheManager<T> {
  private final Map<String, T> cache = new HashMap<>();

  public abstract void sync();

  public Map<String, T> getCache() {
    return cache;
  }
}
