package no.nav.paw.arbeidssokerregisteret.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import no.nav.paw.arbeidssokerregisteret.api.domain.request.EksternRequest
import no.nav.paw.arbeidssokerregisteret.api.services.ArbeidssokerService
import no.nav.paw.arbeidssokerregisteret.api.utils.logger
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun Route.arbeidssokerRoutes(arbeidssokerService: ArbeidssokerService) {
    route("/api/v1") {
        authenticate("maskinporten") {
            route("/arbeidssoker/perioder") {
                post {
                    // Henter arbeidssøkerperiode for bruker
                    logger.info("Henter arbeidssøkerperiode for bruker")

                    val fraOgMed = try {
                        LocalDate.parse(call.request.queryParameters["fraOgMed"] ?: "")
                    } catch (e: DateTimeParseException) {
                        return@post call.respond(HttpStatusCode.BadRequest, "Parameter fraOgMed være satt med dd-mm-yyyy")
                    }
                    val foedselsnummer = call.receive<EksternRequest>().getFoedselsnummer()

                    val arbeidssokerperioder = arbeidssokerService.hentArbeidssokerperioder(foedselsnummer, fraOgMed)

                    logger.info("Startet arbeidssøkerperiode for bruker")

                    call.respond(HttpStatusCode.OK, arbeidssokerperioder)
                }
            }
        }
    }
}
