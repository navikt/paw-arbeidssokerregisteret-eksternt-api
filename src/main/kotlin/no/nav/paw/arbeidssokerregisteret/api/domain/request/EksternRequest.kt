package no.nav.paw.arbeidssokerregisteret.api.domain.request

import no.nav.paw.arbeidssokerregisteret.api.domain.Foedselsnummer
import no.nav.paw.arbeidssokerregisteret.api.domain.toFoedselsnummer

data class EksternRequest(val foedselsnummer: String) {
    fun getFoedselsnummer(): Foedselsnummer = foedselsnummer.toFoedselsnummer()
}
