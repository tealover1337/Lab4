package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddBalanceRequest
import kfd.cherkasov.lab4.models.requests.OperationRequest
import kfd.cherkasov.lab4.services.OperationService
import org.apache.catalina.connector.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/operations")
class OperationController (
    val operationService: OperationService
){

    @PostMapping("/buy")
    fun buyCurrencies(@RequestBody request: OperationRequest) = ResponseEntity
        .status(HttpStatus.OK)
        .body(operationService.proceedDeal(request))

    @PostMapping("/{id}/deposit")
    fun depositCash(@PathVariable("id") userId: Long, @RequestBody request: AddBalanceRequest) = ResponseEntity
        .status(HttpStatus.OK)
        .body(operationService.addBalance(userId, request.currencyId, request.amount))
}