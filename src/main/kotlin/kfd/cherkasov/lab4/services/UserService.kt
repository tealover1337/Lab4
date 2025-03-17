package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.User
import kfd.cherkasov.lab4.database.repositories.RoleDao
import kfd.cherkasov.lab4.database.repositories.UserDao
import kfd.cherkasov.lab4.exceptions.EntityNotFoundException
import kfd.cherkasov.lab4.exceptions.NotUniqueRegisterDataException
import kfd.cherkasov.lab4.mappers.UserMapper
import kfd.cherkasov.lab4.models.requests.RegisterUserRequest
import kfd.cherkasov.lab4.models.requests.UpdateLoginRequest
import kfd.cherkasov.lab4.models.responses.UserDataResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val roleDao: RoleDao
) {
    fun convert(entity: User) = userMapper.entityToResponse(entity)

    fun getAllUsers(): List<UserDataResponse?> = userDao.findAll().map { convert(it) }

    fun getUserById(id: Long): UserDataResponse = convert(userDao.getUserById(id)[0] ?: throw EntityNotFoundException("No such user"))

    @Component
    object PasswordEncoder {
        fun getPasswordEncoder() = BCryptPasswordEncoder(10)
    }

    @Transactional
    fun createUser(registerData: RegisterUserRequest) : UserDataResponse {
        val encodedPassword = PasswordEncoder.getPasswordEncoder().encode(registerData.password)

        try { if (userDao.findUserByLogin(registerData.login)[0] != null) {
            throw NotUniqueRegisterDataException("Login already exists")
        }
        } catch (e: IndexOutOfBoundsException) {}

        val createdUser = User(
            passwordHash = encodedPassword
        )
        createdUser.login = registerData.login
        val defaultRole = roleDao.findByName("ROLE_USER")[0]
        createdUser.roles.add(defaultRole)
        userDao.save(createdUser)

        return convert(createdUser)
    }

    @Transactional
    fun updateLogin(id: Long, request: UpdateLoginRequest): UserDataResponse {
        val updatedUser = userDao.getUserById(id)[0] ?: throw EntityNotFoundException("No such user")
        updatedUser.login = request.login
        userDao.save(updatedUser)
        return userMapper.entityToResponse(updatedUser)
    }

    fun deleteUser(id: Long): UserDataResponse {
        val body = convert(userDao.getUserById(id)[0] ?: throw EntityNotFoundException("No such user"))
        userDao.deleteById(id)
        return body
    }
}