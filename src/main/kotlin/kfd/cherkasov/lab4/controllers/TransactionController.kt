package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddTransactionRequest
import kfd.cherkasov.lab4.services.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionController (
    val transactionService: TransactionService
) {
    @GetMapping
    fun getAllTransactions() = ResponseEntity
        .status(HttpStatus.OK)
        .body(transactionService.getAllTransactions())

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.OK)
        .body(transactionService.getTransactionById(id))

    @PostMapping
    fun addTransaction(@RequestBody request: AddTransactionRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(transactionService.addTransaction(request))

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(transactionService.deleteTransactionById(id))
}