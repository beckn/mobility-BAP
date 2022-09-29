package org.beckn.one.sandbox.bap.client.order.quote.services

import arrow.core.Either
import arrow.core.flatMap
import com.google.gson.GsonBuilder
import org.beckn.one.sandbox.bap.client.order.quote.mapper.SelectedItemMapper
import org.beckn.one.sandbox.bap.client.shared.dtos.CartDto
import org.beckn.one.sandbox.bap.client.shared.dtos.CartItemDto
import org.beckn.one.sandbox.bap.client.shared.errors.CartError
import org.beckn.one.sandbox.bap.client.shared.services.RegistryService
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuoteService @Autowired constructor(
  private val registryService: RegistryService,
  private val bppSelectService: ProtocolSelectService,
  private val selectedItemMapper: SelectedItemMapper,
) {
  private val log: Logger = LoggerFactory.getLogger(QuoteService::class.java)

  fun getQuote(context: ProtocolContext, cart: CartDto): Either<HttpError, ProtocolAckResponse?> {
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    log.info("Got get quote request. Context: {}, Cart: {}", gsonPretty.toJson(context), gsonPretty.toJson(cart))
    if (cart.items.isNullOrEmpty()) {
      log.info("Empty cart received, not doing anything. Cart: {}", cart)
      return Either.Right(null)
    }

    return bppSelectService.select(
      context,
      items = cart.items
        //.map { cartItem -> selectedItemMapper.dtoToProtocol(cartItem) }
    )
  }
}
