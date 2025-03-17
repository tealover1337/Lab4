package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.RegisterUserRequest
import kfd.cherkasov.lab4.models.requests.UpdateLoginRequest
import kfd.cherkasov.lab4.models.responses.UserDataResponse
import kfd.cherkasov.lab4.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (val userService: UserService) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDataResponse?>> = ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getAllUsers())

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long): ResponseEntity<UserDataResponse> = ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getUserById(id))

    @PostMapping
    fun createUser(@RequestBody request: RegisterUserRequest): ResponseEntity<UserDataResponse> = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userService.createUser(request))

    @PutMapping("/{id}")
    fun updateLogin(@PathVariable("id") id: Long, @RequestBody request: UpdateLoginRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userService.updateLogin(id, request))

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") id: Long): ResponseEntity<UserDataResponse> = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(userService.deleteUser(id))
}