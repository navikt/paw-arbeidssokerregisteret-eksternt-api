plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jmailen.kotlinter") version "3.16.0"
    application
}

val logbackVersion = "1.4.5"
val logstashVersion = "7.3"
val navCommonModulesVersion = "2.2023.01.02_13.51-1c6adeb1653b"
val tokenSupportVersion = "3.1.5"
val koTestVersion = "5.7.2"
val hopliteVersion = "2.7.5"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
    mavenNav("paw-arbeidssokerregisteret-api")
}

dependencies {
    implementation(pawObservability.bundles.ktorNettyOpentelemetryMicrometerPrometheus)
    implementation("no.nav.security:token-validation-ktor-v2:$tokenSupportVersion")
    implementation("no.nav.security:token-client-core:$tokenSupportVersion")
    implementation("no.nav.common:token-client:$navCommonModulesVersion")
    implementation("no.nav.common:log:$navCommonModulesVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hopliteVersion")

    // TODO: Flytte til bundle KTOR
    val ktorVersion = "2.3.4"
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-call-id:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.testcontainers:testcontainers:1.19.1")
    testImplementation("no.nav.security:mock-oauth2-server:2.0.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    "run"(JavaExec::class) {
        jvmArgs = listOf(
            "-javaagent:agents/opentelemetry-javaagent.jar",
            "-Dotel.javaagent.extensions=agents/opentelemetry-anonymisering-1.30.0-23.09.22.7-1.jar",
            "-Dotel.resource.attributes=service.name=paw-arbeidssokerregisteret",
        )
        environment("OTEL_TRACES_EXPORTER", "maskert_oltp")
        environment("OTEL_METRICS_EXPORTER", "none")
        environment("OTEL_JAVAAGENT_DEBUG", "false")
    }
}

application {
    mainClass.set("no.nav.paw.arbeidssokerregisteret.api.ApplicationKt")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

fun RepositoryHandler.mavenNav(repo: String): MavenArtifactRepository {
    val githubPassword: String by project

    return maven {
        setUrl("https://maven.pkg.github.com/navikt/$repo")
        credentials {
            username = "x-access-token"
            password = githubPassword
        }
    }
}
