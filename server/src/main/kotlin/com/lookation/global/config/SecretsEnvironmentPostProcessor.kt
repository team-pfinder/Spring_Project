package com.lookation.global.config

import org.springframework.boot.EnvironmentPostProcessor
import org.springframework.boot.SpringApplication
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor
import org.springframework.core.Ordered
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import tools.jackson.databind.json.JsonMapper
import java.net.URI
import java.time.Duration

/**
 * Loads secrets from AWS Secrets Manager (MiniStack locally) before placeholder resolution.
 *
 * Runs after [ConfigDataEnvironmentPostProcessor] so `application-local.yml` is visible, and
 * publishes the values under the exact names `application.yml` references. Inactive unless
 * `lookation.secrets.enabled=true`, which only the local profile sets.
 */
class SecretsEnvironmentPostProcessor : EnvironmentPostProcessor, Ordered {

    override fun getOrder(): Int = ConfigDataEnvironmentPostProcessor.ORDER + 1

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        if (!environment.getProperty(ENABLED_PROPERTY, "false").toBoolean()) return

        val secretJson = fetchSecret(environment)
        val node = JsonMapper.builder().build().readTree(secretJson)
        val properties = SECRET_FIELDS.mapValues { (property, field) ->
            node.path(field).asString().ifEmpty {
                throw IllegalStateException("Secret ${required(environment, "$PREFIX.name")} has no '$field'")
            }
        }
        environment.propertySources.addFirst(MapPropertySource("lookationSecrets", properties))
    }

    private fun fetchSecret(environment: ConfigurableEnvironment): String {
        val endpoint = required(environment, "$PREFIX.endpoint")
        val region = required(environment, "$PREFIX.region")
        val accessKey = required(environment, "$PREFIX.access-key")
        val secretKey = required(environment, "$PREFIX.secret-key")
        val name = required(environment, "$PREFIX.name")

        return SecretsManagerClient.builder()
            .endpointOverride(URI.create(endpoint))
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)),
            )
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .overrideConfiguration { it.apiCallTimeout(CALL_TIMEOUT) }
            .build()
            .use { client ->
                client.getSecretValue(GetSecretValueRequest.builder().secretId(name).build()).secretString()
            }
    }

    private fun required(environment: ConfigurableEnvironment, property: String): String =
        environment.getProperty(property)?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("$property is required when $ENABLED_PROPERTY=true")

    private companion object {
        const val PREFIX = "lookation.secrets"
        const val ENABLED_PROPERTY = "$PREFIX.enabled"
        val CALL_TIMEOUT: Duration = Duration.ofSeconds(3)

        /** Secret JSON field -> property name that `application.yml` resolves through `${...}`. */
        val SECRET_FIELDS = mapOf(
            "LOOKATION_DB_PASSWORD" to "dbPassword",
            "LOOKATION_JWT_SECRET" to "jwtSecret",
            "LOOKATION_ENCRYPTION_KEY" to "encryptionKey",
            "LOOKATION_ADMIN_PASSWORD" to "adminPassword",
        )
    }
}
