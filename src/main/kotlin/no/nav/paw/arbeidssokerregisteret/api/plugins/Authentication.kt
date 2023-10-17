package no.nav.paw.arbeidssokerregisteret.api.plugins

import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import no.nav.paw.arbeidssokerregisteret.api.config.AuthProviders
import no.nav.security.token.support.v2.IssuerConfig
import no.nav.security.token.support.v2.RequiredClaims
import no.nav.security.token.support.v2.TokenSupportConfig
import no.nav.security.token.support.v2.tokenValidationSupport

fun Application.configureAuthentication(authProviders: AuthProviders) {
    val (maskinporten) = authProviders
    authentication {
        tokenValidationSupport(
            name = maskinporten.name,
            requiredClaims = RequiredClaims(maskinporten.name, maskinporten.claims.toTypedArray()),
            config = TokenSupportConfig(
                IssuerConfig(
                    name = maskinporten.name,
                    discoveryUrl = maskinporten.discoveryUrl,
                    acceptedAudience = listOf(maskinporten.clientId)
                )
            )
        )
    }
}
