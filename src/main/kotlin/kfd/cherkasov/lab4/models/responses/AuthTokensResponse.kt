package kfd.cherkasov.lab4.models.responses

data class AuthTokensResponse (
    val accessToken: String,
    val refreshToken: String
)