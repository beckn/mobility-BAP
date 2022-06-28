package org.beckn.one.sandbox.bap.client.order.confirm.services

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import org.beckn.one.sandbox.bap.client.external.hasBody
import org.beckn.one.sandbox.bap.client.external.isAckNegative
import org.beckn.one.sandbox.bap.client.external.isInternalServerError
import org.beckn.one.sandbox.bap.client.external.provider.BppClient
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.shared.dtos.OrderDto
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.protocol.schemas.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class ProtocolConfirmService @Autowired constructor(
  private val bppServiceClientFactory: ProtocolClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(ProtocolConfirmService::class.java)

  fun confirm(context: ProtocolContext, order: OrderDto): Either<BppError, ProtocolAckResponse> {
    return Either.catch {
      log.info("Invoking Confirm API on protocol server: {}")
      val bppServiceClient = bppServiceClientFactory.getClient(null)
      val httpResponse =
        invokeBppConfirmApi(
          bppServiceClient = bppServiceClient,
          context = context,
          order = order
        )
      log.info("BPP confirm API response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
      return when {
        httpResponse.isInternalServerError() -> Left(BppError.Internal)
        !httpResponse.hasBody() -> Left(BppError.NullResponse)
        httpResponse.isAckNegative() -> Left(BppError.Nack)
        else -> Right(httpResponse.body()!!)
      }
    }.mapLeft {
      log.error("Error when initiating confirm", it)
      BppError.Internal
    }
  }

  private fun invokeBppConfirmApi(
    bppServiceClient: BppClient,
    context: ProtocolContext,
    order: OrderDto
  ): Response<ProtocolAckResponse> {
    val confirmRequest = ProtocolConfirmRequest(
      context = context,
      ProtocolConfirmRequestMessage(
        order = ProtocolOrder(
          provider = order.items?.first()?.provider?.let {
            ProtocolSelectMessageSelectedProvider(
              id = it?.id,
              locations = listOf(ProtocolSelectMessageSelectedProviderLocations(id = it?.locations!!.first()))
            )
          },
          items = order.items!!.map { ProtocolSelectMessageSelectedItems(id = it.id, quantity = it.quantity,  ondcReturnWindow = null,
            ondcStatutoryPackagedFood = null, ondcStatutoryPackagedCommodities = null) },
          billing = order.billingInfo,
          fulfillment = ProtocolFulfillment(
            end = ProtocolFulfillmentEnd(
              contact = ProtocolContact(
                phone = order.deliveryInfo.phone,
                email = order.deliveryInfo.email
              ), location = order.deliveryInfo.location
            ),
            type = "home_delivery",
            customer = ProtocolCustomer(person = ProtocolPerson(name = order.deliveryInfo.name)),
            provider_id = order.items?.first()?.provider?.id
          ),
          addOns = emptyList(),
          offers = emptyList(),
          payment = ProtocolPayment(
            params = mapOf("amount" to order.payment!!.paidAmount.toString(),
            "currency" to order.payment.currency
            ),
            status = ProtocolPayment.Status.PAID
          )
        )
      )
    )
    log.info("Confirm API request body: {}", confirmRequest)
    return bppServiceClient.confirm(confirmRequest).execute()
  }
}