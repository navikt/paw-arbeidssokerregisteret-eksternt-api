package no.nav.paw.arbeidssokerregisteret.api

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import no.nav.paw.arbeidssokerregisteret.api.config.Config
import no.nav.paw.arbeidssokerregisteret.api.services.ArbeidssokerService

fun createDependencies(config: Config): Dependencies {
    val registry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    val arbeidssokerService = ArbeidssokerService()

    return Dependencies(
        registry,
        arbeidssokerService
    )
}

data class Dependencies(
    val registry: PrometheusMeterRegistry,
    val arbeidssokerService: ArbeidssokerService
)
