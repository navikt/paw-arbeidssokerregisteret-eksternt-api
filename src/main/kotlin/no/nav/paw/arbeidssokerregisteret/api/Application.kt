package no.nav.paw.arbeidssokerregisteret.api

import io.ktor.server.application.Application
import io.ktor.server.engine.addShutdownHook
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import no.nav.paw.arbeidssokerregisteret.api.config.Config
import no.nav.paw.arbeidssokerregisteret.api.config.loadConfiguration
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureAuthentication
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureHTTP
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureLogging
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureMetrics
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureSerialization
import no.nav.paw.arbeidssokerregisteret.api.routes.arbeidssokerRoutes
import no.nav.paw.arbeidssokerregisteret.api.routes.healthRoutes
import no.nav.paw.arbeidssokerregisteret.api.routes.swaggerRoutes

fun main() {
    val server = embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

    server.addShutdownHook {
        server.stop(300, 300)
    }
}

fun Application.module() {
    // Konfigurasjon
    val config = loadConfiguration<Config>()

    // Avhengigheter
    val dependencies = createDependencies(config)

    // Konfigurerer plugins
    configureMetrics(dependencies.registry)
    configureHTTP()
    configureAuthentication(config.authProviders)
    configureLogging()
    configureSerialization()

    // Ruter
    routing {
        healthRoutes(dependencies.registry)
        swaggerRoutes()
        arbeidssokerRoutes(dependencies.arbeidssokerService)
    }
}
