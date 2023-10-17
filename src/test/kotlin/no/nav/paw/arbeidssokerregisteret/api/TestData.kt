package no.nav.paw.arbeidssokerregisteret.api

import no.nav.paw.arbeidssokerregisteret.api.domain.toFoedselsnummer

object TestData {
    val foedselsnummer = "18908396568".toFoedselsnummer()
    val maskinportenScope = "nav:arbeid:arbeidssokerregisteret.read"
}
