package no.nav.paw.arbeidssokerregisteret.api.config

data class Config(
    val authProviders: AuthProviders,
    val naisEnv: NaisEnv = currentNaisEnv
)

data class AuthProviders(
    val maskinporten: AuthProvider
)

data class AuthProvider(
    val name: String,
    val discoveryUrl: String,
    val tokenEndpointUrl: String,
    val clientId: String,
    val claims: List<String>
)

data class ServiceClientConfig(
    val url: String,
    val scope: String
)
