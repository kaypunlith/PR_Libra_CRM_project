package com.ut.nlSystemAPi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class GlobalConfigLoader {
  private static final String BASE_RESOURCE = "global.properties";
  private static final String PROFILE_PREFIX = "global-";
  private static final String PROFILE_SUFFIX = ".properties";
  private static final String PROFILE_KEY = "global.profile";
  private static final String DEFAULT_PROFILE = "dev";

  private GlobalConfigLoader() {
  }

  public static Map<String, Object> loadDefaultProperties() {
    Properties baseProps = loadProperties(BASE_RESOURCE);
    String profile = resolveProfile(baseProps);

    Properties merged = new Properties();
    merged.putAll(baseProps);
    merged.putAll(loadProperties(PROFILE_PREFIX + profile + PROFILE_SUFFIX));
    merged.setProperty("spring.profiles.active", profile);

    Map<String, Object> defaults = new LinkedHashMap<>();
    for (String name : merged.stringPropertyNames()) {
      defaults.put(name, merged.getProperty(name));
    }
    return defaults;
  }

  private static String resolveProfile(Properties baseProps) {
    String profile = System.getProperty("spring.profiles.active");
    if (isBlank(profile)) {
      profile = System.getenv("SPRING_PROFILES_ACTIVE");
    }
    if (isBlank(profile)) {
      profile = baseProps.getProperty(PROFILE_KEY);
    }
    if (isBlank(profile)) {
      profile = DEFAULT_PROFILE;
    }
    profile = profile.trim();
    int commaIndex = profile.indexOf(',');
    if (commaIndex >= 0) {
      profile = profile.substring(0, commaIndex).trim();
    }
    if (isBlank(profile)) {
      profile = DEFAULT_PROFILE;
    }
    return profile;
  }

  private static Properties loadProperties(String resourceName) {
    Properties properties = new Properties();
    try (InputStream stream = GlobalConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
      if (stream != null) {
        properties.load(stream);
      }
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load " + resourceName, ex);
    }
    return properties;
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}
