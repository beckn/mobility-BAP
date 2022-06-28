package org.beckn.one.sandbox.bap.client.shared.controllers

import org.beckn.one.sandbox.bap.client.shared.dtos.ClientErrorResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientResponse
import org.beckn.one.sandbox.bap.client.shared.services.GenericClientOnPollService
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity

open class AbstractClientOnPollController<Protocol: ProtocolResponse, Output: ClientResponse>(
  private val onPollService: GenericClientOnPollService<Protocol, Output>,
  private val contextFactory: ContextFactory,
  private val loggingFactory: LoggingFactory,
  private val loggingService: LoggingService
) {
  private val log: Logger = LoggerFactory.getLogger(this::class.java)

  fun onPoll(
    context: ProtocolContext,
    providerName: String?,
    categoryName: String?,
    orderId: String?,
    action: ProtocolContext.Action
  ): ResponseEntity<out ClientResponse> = onPollService
    .onPoll(context, providerName, categoryName, orderId, action)
    .fold(
      {
        log.error("Error when finding response by message id. Error: {}", it)
        val messageId = context.messageId
        val context = contextFactory.create(messageId = messageId)
        val loggerRequest = loggingFactory.create(messageId = messageId, transactionId = context.transactionId,
          contextTimestamp = context.timestamp.toString(),
          action = action, bppId = context.bppId,errorCode = it.error().code, errorMessage = it.error().code
        )
        loggingService.postLog(loggerRequest)
        ResponseEntity
          .status(it.status().value())
          .body(ClientErrorResponse(context = context, error = it.error()))
      },
      {
        val messageId = context.messageId
        val context = contextFactory.create(messageId = messageId)
        log.info("Found responses for message {}", messageId)
        val loggerRequest = loggingFactory.create(messageId = messageId, transactionId = context.transactionId, contextTimestamp = context.timestamp.toString(),
          action = action, bppId = context.bppId
        )
        loggingService.postLog(loggerRequest)
        ResponseEntity.ok(it)
      }
    )
}