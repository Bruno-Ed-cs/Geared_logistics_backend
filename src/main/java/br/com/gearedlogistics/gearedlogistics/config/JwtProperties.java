package br.com.gearedlogistics.gearedlogistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Configurações do token JWT (segredo e tempo de expiração).
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, Duration expiration) {
}
