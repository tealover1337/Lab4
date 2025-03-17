package kfd.cherkasov.lab4.models.requests

data class AuthoriseUserRequest (
    val login: String,
    val password: String
)