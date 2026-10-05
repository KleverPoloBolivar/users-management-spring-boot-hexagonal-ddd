package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import java.util.Locale;

public record DatabaseConfig(
    String host,
    int port,
    String databaseName,
    String username,
    String password,
    String sslMode) {
  private static final String URL_TEMPLATE =
      "jdbc:mysql://%s:%d/%s?sslMode=%s&serverTimezone=UTC&allowPublicKeyRetrieval=true";

  private static final String POSTGRES_URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s?sslmode=%s";

  public String buildJdbcUrl() {
    return String.format(URL_TEMPLATE, host, port, databaseName, sslMode);
  }

  public String buildPostgresJdbcUrl() {
    return String.format(
        POSTGRES_URL_TEMPLATE, host, port, databaseName, toPostgresSslMode(sslMode));
  }

  private static String toPostgresSslMode(final String mode) {
    if (mode == null || mode.isBlank() || "DISABLED".equalsIgnoreCase(mode)) {
      return "disable";
    }
    if ("REQUIRED".equalsIgnoreCase(mode)) {
      return "require";
    }
    return mode.toLowerCase(Locale.ROOT);
  }
}