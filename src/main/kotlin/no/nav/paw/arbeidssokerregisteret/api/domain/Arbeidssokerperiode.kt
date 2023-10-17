package no.nav.paw.arbeidssokerregisteret.api.domain

import java.time.LocalDateTime

data class Arbeidssokerperiode(
    val fraOgMed: LocalDateTime,
    val tilOgMed: LocalDateTime? = null
)
