package kfd.cherkasov.lab4

import kfd.cherkasov.lab4.database.entities.UserRole
import kfd.cherkasov.lab4.database.repositories.RoleDao
import kfd.cherkasov.lab4.database.repositories.UserDao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Lab4Application

fun main(args: Array<String>) {
    runApplication<Lab4Application>(*args)
}

