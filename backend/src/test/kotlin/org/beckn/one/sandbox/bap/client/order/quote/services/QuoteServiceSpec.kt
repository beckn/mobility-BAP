package org.beckn.one.sandbox.bap.client.order.quote.services

import arrow.core.Either
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import org.beckn.one.sandbox.bap.client.factories.CartFactory
import org.beckn.one.sandbox.bap.client.order.quote.mapper.SelectedItemMapperImpl
import org.beckn.one.sandbox.bap.client.shared.dtos.CartDto
import org.beckn.one.sandbox.bap.client.shared.errors.CartError
import org.beckn.one.sandbox.bap.common.factories.ContextFactoryInstance
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolSelectedItem
import org.beckn.protocol.schemas.ResponseMessage
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verifyNoMoreInteractions

class QuoteServiceSpec : DescribeSpec() {
  private val protocolSelectService = mock(ProtocolSelectService::class.java)
  private val context = ContextFactoryInstance.create().create()
  private  val mapper = SelectedItemMapperImpl()
  private val quoteService = QuoteService(
    bppSelectService = protocolSelectService,
    selectedItemMapper = mapper
  )

  init {
    describe("Get quote") {

      it("should return success with null message when cart is empty") {
        val quote = quoteService.getQuote(
          context = context,
          cart = CartDto(items = listOf())
        )

        quote shouldBeRight null
        verifyNoMoreInteractions(protocolSelectService)
      }

      it("should return error when multiple BPP items are part of the cart") {
        val cartWithMultipleBppItems = CartFactory.create(bpp1Uri = "www.bpp1.com", bpp2Uri = "www.bpp2.com")

        val quote = quoteService.getQuote(
          context = context,
          cart = cartWithMultipleBppItems
        )

        quote shouldBeLeft CartError.MultipleBpps
        verifyNoMoreInteractions(protocolSelectService)
      }

      it("should return error when multiple Provider items are part of the cart") {
        val cartWithMultipleProviderItems =
          CartFactory.create(
            bpp1Uri = "www.bpp1.com",
            provider2Id = "padma coffee works",
            provider2Location = listOf("padma coffee works location 1")
          )

        val quote = quoteService.getQuote(
          context = context,
          cart = cartWithMultipleProviderItems
        )

        quote shouldBeLeft CartError.MultipleProviders
        verifyNoMoreInteractions(protocolSelectService)
      }

      it("should return successfully") {
        val cartWithMultipleProviderItems =
          CartFactory.create(
            bpp1Uri = "www.bpp1.com"
          )
        val item : List<ProtocolSelectedItem> = cartWithMultipleProviderItems.items!!.map { cartItem -> mapper.dtoToProtocol(cartItem) }
        `when`(protocolSelectService.select(context, item)).thenReturn(Either.Right(ProtocolAckResponse(context, ResponseMessage.ack())))

        val quote = quoteService.getQuote(
          context = context,
          cart = cartWithMultipleProviderItems
        )


        quote shouldBeRight ProtocolAckResponse(context, ResponseMessage.ack())
      }
    }
  }
}