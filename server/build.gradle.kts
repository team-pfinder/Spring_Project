plugins {
    id("org.springframework.boot") version "4.1.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
}

group = "com.lookation"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        allWarningsAsErrors = true
    }
}

repositories {
    mavenCentral()
}

val jooqVersion = "3.21.9"

val jooqCodegen = configurations.create("jooqCodegen")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.bouncycastle:bcprov-jdk18on:1.86")
    implementation("org.springframework.boot:spring-boot-flyway")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.2.0")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation(platform("software.amazon.awssdk:bom:2.46.7"))
    implementation("software.amazon.awssdk:secretsmanager")
    implementation("software.amazon.awssdk:url-connection-client")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(platform("org.testcontainers:testcontainers-bom:2.0.5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")

    jooqCodegen("org.jooq:jooq-codegen:$jooqVersion")
    jooqCodegen("org.jooq:jooq-meta-extensions:$jooqVersion")
    jooqCodegen("org.flywaydb:flyway-core")
}

val generatedJooqDir = layout.buildDirectory.dir("generated-sources/jooq")

val generateJooq = tasks.register<JavaExec>("generateJooq") {
    group = "build"
    description = "Generates jOOQ sources from the Flyway baseline DDL."
    classpath = jooqCodegen
    mainClass = "org.jooq.codegen.GenerationTool"
    args("jooq-codegen.xml")
    inputs.files(fileTree("src/main/resources/db/migration"))
    inputs.file("jooq-codegen.xml")
    outputs.dir(generatedJooqDir)
}

sourceSets {
    main {
        java.srcDir(generatedJooqDir)
    }
}

tasks.named("compileJava") {
    dependsOn(generateJooq)
}

tasks.named("compileKotlin") {
    dependsOn(generateJooq)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
