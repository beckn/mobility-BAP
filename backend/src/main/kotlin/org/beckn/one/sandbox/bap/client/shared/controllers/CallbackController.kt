package org.beckn.one.sandbox.bap.client.shared.controllers

import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolResponse
import org.beckn.protocol.schemas.ResponseMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

open class AbstractCallbackController<Protocol: ProtocolResponse> @Autowired constructor(
  private val storage: ProtocolResponseStorageService<Protocol>,
  private val loggingFactory: LoggingFactory,
  private val loggingService: LoggingService
) {
  private val log = LoggerFactory.getLogger(AbstractCallbackController::class.java)

  fun onCallback(@RequestBody callBackActionResponse: Protocol, action: ProtocolContext.Action) = storage
    .save(callBackActionResponse)
    .fold(
      ifLeft = {
        log.error("Error during persisting. Error: {}", it)
        val loggerRequest = loggingFactory.create(action = action, errorMessage = it.error().message, errorCode = it.error().code)
        loggingService.postLog(loggerRequest)
        ResponseEntity
          .status(it.status().value())
          .body(ProtocolAckResponse(null, it.message(), it.error()))
      },
      ifRight = {
        val loggerRequest = loggingFactory.create(messageId = callBackActionResponse.context?.messageId?:"", transactionId = callBackActionResponse.context?.transactionId?:"",
          contextTimestamp = callBackActionResponse.context?.timestamp.toString(),
          action = action, bppId = callBackActionResponse.context?.bppId
        )
        loggingService.postLog(loggerRequest)
        log.info("Successfully persisted response with message id: ${callBackActionResponse.context?.messageId}")
        ResponseEntity.ok(ProtocolAckResponse(null, ResponseMessage.ack()))
      }
    )
}