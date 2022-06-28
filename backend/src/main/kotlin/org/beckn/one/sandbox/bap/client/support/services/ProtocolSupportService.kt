package org.beckn.one.sandbox.bap.client.support.services

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
import org.beckn.protocol.schemas.ProtocolSupportRequest
import org.beckn.protocol.schemas.ProtocolSupportRequestMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProtocolSupportService @Autowired constructor(
  private val bppServiceClientFactory: ProtocolClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(ProtocolSupportService::class.java)

  fun support(context: ProtocolContext, refId: String): Either<BppError, ProtocolAckResponse> =
    Either.catch {
      log.info("Invoking support API on Protocol Server: {}")
      val bppServiceClient = bppServiceClientFactory.getClient(null)
      val httpResponse =
        bppServiceClient.support(
          ProtocolSupportRequest(
            context = context,
            message = ProtocolSupportRequestMessage(refId = refId)
          )
        ).execute()
      log.info("BPP support API response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
      return when {
        httpResponse.isInternalServerError() -> Left(BppError.Internal)
        !httpResponse.hasBody() -> Left(BppError.NullResponse)
        httpResponse.isAckNegative() -> Left(BppError.Nack)
        else -> Right(httpResponse.body()!!)
      }
    }.mapLeft {
      log.error("Error when invoking BPP Support API", it)
      BppError.Internal
    }
}