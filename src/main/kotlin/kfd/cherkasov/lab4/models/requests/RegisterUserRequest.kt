package kfd.cherkasov.lab4.models.requests

data class RegisterUserRequest (
    val login: String,
    val password: String
)