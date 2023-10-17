package no.nav.paw.arbeidssokerregisteret.api.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import no.nav.paw.arbeidssokerregisteret.api.config.Config
import no.nav.paw.arbeidssokerregisteret.api.config.loadConfiguration
import no.nav.paw.arbeidssokerregisteret.api.plugins.configureAuthentication
import no.nav.security.mock.oauth2.MockOAuth2Server

fun Application.testAuthModule(oAuth2Server: MockOAuth2Server) {
    val config = loadConfiguration<Config>()
    val authProviders = config.authProviders.copy(
        maskinporten = config.authProviders.maskinporten.copy(
            discoveryUrl = oAuth2Server.wellKnownUrl("default").toString(),
            clientId = "default"
        )
    )
    configureAuthentication(authProviders)
    routing {
        authenticate("maskinporten") {
            route("/testAuthMaskinporten") {
                get {
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
