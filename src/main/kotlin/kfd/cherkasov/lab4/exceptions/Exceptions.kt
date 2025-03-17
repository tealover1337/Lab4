package kfd.cherkasov.lab4.exceptions

class CredentialMismatchException(message: String) : RuntimeException(message)
class EntityNotFoundException(message: String) : RuntimeException(message)
class NotUniqueRegisterDataException(message: String) : RuntimeException(message)
class UnauthorizedException(message: String) : RuntimeException(message)
class InvalidTokenException(message: String) : RuntimeException(message)
class TokenExpiredException(message: String) : RuntimeException(message)