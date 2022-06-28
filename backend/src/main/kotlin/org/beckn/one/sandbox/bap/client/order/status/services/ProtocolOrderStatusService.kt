package org.beckn.one.sandbox.bap.client.order.status.services

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import org.beckn.one.sandbox.bap.client.external.hasBody
import org.beckn.one.sandbox.bap.client.external.isAckNegative
import org.beckn.one.sandbox.bap.client.external.isInternalServerError
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOrderStatusRequest
import org.beckn.protocol.schemas.ProtocolOrderStatusRequestMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProtocolOrderStatusService @Autowired constructor(
  private val bppServiceClientFactory: ProtocolClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(ProtocolOrderStatusService::class.java)

  fun getOrderStatus(context: ProtocolContext, message: ProtocolOrderStatusRequestMessage):
      Either<BppError, ProtocolAckResponse> = Either.catch {
    log.info("Invoking Order Status API on Protocol Server: {}")
    val bppServiceClient = bppServiceClientFactory.getClient(null)
    val httpResponse =
      bppServiceClient.getOrderStatus(ProtocolOrderStatusRequest(context = context, message = message))
        .execute()
    log.info("Protocol Server Get Order Status API response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
    return when {
      httpResponse.isInternalServerError() -> Left(BppError.Internal)
      !httpResponse.hasBody() -> Left(BppError.NullResponse)
      httpResponse.isAckNegative() -> Left(BppError.Nack)
      else -> Right(httpResponse.body()!!)
    }
  }.mapLeft {
    log.error("Error when invoking BPP Get Order Status API", it)
    BppError.Internal
  }
}