package org.beckn.one.sandbox.bap.client.shared.services

import arrow.core.Either
import arrow.core.flatMap
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientResponse
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class GenericClientOnPollService<Protocol : ProtocolResponse, Output : ClientResponse> constructor(
  private val pollForService: PollForResponseService<Protocol>,
  private val transformer: GenericOnPollMapper<Protocol, Output>
) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)

  open fun onPoll(
    context: ProtocolContext,
    providerName: String?,
    categoryName: String?,
    orderId: String?,
    action: ProtocolContext.Action
  ): Either<HttpError, Output,> {
    log.info("Got fetch request for message id: {}", context.messageId)
    return when (action) {
      ProtocolContext.Action.ON_SEARCH -> {
        pollForService.findSearchCatalog( context.messageId, providerName, categoryName).flatMap {
          transformer.transform(it, context)
        }
      }
      ProtocolContext.Action.ON_STATUS -> {
        pollForService.findResponsesByOrderId(orderId ?: "").flatMap {
          transformer.transform(it, context)
        }
      }
      else -> {
        pollForService.findResponses(context.messageId).flatMap {
          transformer.transform(it, context)
        }
      }
    }
  }
}

interface GenericOnPollMapper<in Protocol : ProtocolResponse, out Output : ClientResponse> {
  fun transform(input: List<Protocol>, context: ProtocolContext): Either<HttpError, Output>
}