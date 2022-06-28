package org.beckn.one.sandbox.bap.client.order.quote.services

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.flatMap
import org.beckn.one.sandbox.bap.client.external.hasBody
import org.beckn.one.sandbox.bap.client.external.isAckNegative
import org.beckn.one.sandbox.bap.client.external.isInternalServerError
import org.beckn.one.sandbox.bap.client.external.provider.BppClient
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.shared.dtos.CartDto
import org.beckn.one.sandbox.bap.client.shared.dtos.CartItemDto
import org.beckn.one.sandbox.bap.client.shared.errors.CartError
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.protocol.schemas.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class ProtocolSelectService @Autowired constructor(
  private val bppServiceClientFactory: ProtocolClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(ProtocolSelectService::class.java)

  fun select(
    context: ProtocolContext,
    providerId: String,
    providerLocation: ProtocolLocation,
    items: List<ProtocolSelectedItem>
  ): Either<BppError, ProtocolAckResponse> {
    return Either
      .catch {
        log.info("Invoking Select API on Protocol Server: {}")
        val bppServiceClient = bppServiceClientFactory.getClient(null)
        val httpResponse = invokeBppSelectApi(bppServiceClient, context, providerId, providerLocation, items)
        log.info("Protocol Server Select API response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
        return when {
          httpResponse.isInternalServerError() -> Left(BppError.Internal)
          !httpResponse.hasBody() -> Left(BppError.NullResponse)
          httpResponse.isAckNegative() -> Left(BppError.Nack)
          else -> Right(httpResponse.body()!!)
        }
      }.mapLeft {
        log.error("Error when initiating select", it)
        BppError.Internal
      }
  }

  private fun invokeBppSelectApi(
    bppServiceClient: BppClient,
    context: ProtocolContext,
    providerId: String,
    providerLocation: ProtocolLocation,
    items: List<ProtocolSelectedItem>
  ): Response<ProtocolAckResponse> {
    val selectRequest = ProtocolSelectRequest(
      context = context,
      ProtocolSelectRequestMessage(
        order = ProtocolSelectMessageSelected(
          provider = ProtocolProvider(id = providerId, locations = listOf(providerLocation)),
          items = items
        )
      )
    )
    log.info("Select API request body: {}", selectRequest)
    return bppServiceClient.select(selectRequest).execute()
  }
}