package no.nav.paw.arbeidssokerregisteret.api.services

import no.nav.paw.arbeidssokerregisteret.api.domain.Arbeidssokerperiode
import no.nav.paw.arbeidssokerregisteret.api.domain.Foedselsnummer
import java.time.LocalDate

class ArbeidssokerService {
    fun hentArbeidssokerperioder(foedselsnummer: Foedselsnummer, fraOgMed: LocalDate): List<Arbeidssokerperiode> {
        return emptyList()
    }
}
