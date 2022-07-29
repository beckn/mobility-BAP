package org.beckn.one.sandbox.bap.client.order.quote.services

import arrow.core.Either
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.core.spec.style.DescribeSpec
import org.beckn.one.sandbox.bap.client.external.provider.BppClient
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.common.factories.ContextFactoryInstance
import org.beckn.one.sandbox.bap.message.factories.IdFactory
import org.beckn.one.sandbox.bap.message.factories.ProtocolLocationFactory
import org.beckn.one.sandbox.bap.message.factories.ProtocolSelectedItemFactory
import org.beckn.one.sandbox.bap.factories.UuidFactory
import org.beckn.protocol.schemas.*
import org.mockito.Mockito.*
import retrofit2.mock.Calls
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

internal class ProtocolSelectServiceSpec : DescribeSpec() {
    private val protocolClientFactory = mock(ProtocolClientFactory::class.java)
    private val clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"))
    private val uuidFactory = mock(UuidFactory::class.java)
    private val contextFactory = ContextFactoryInstance.create(uuidFactory, clock)
    private val protocolSelectService = ProtocolSelectService(protocolClientFactory)
    private val protocolClient: BppClient = mock(BppClient::class.java)
    private val bppUri = "https://bpp1.com"

    init {
        describe("Select") {
            `when`(uuidFactory.create()).thenReturn("9056ea1b-275d-4799-b0c8-25ae74b6bf51")
            `when`(protocolClientFactory.getClient(null)).thenReturn(protocolClient)
            val selectRequest = getSelectRequest()
            val listItem = IdFactory.forItems(IdFactory.forProvider(1), 1).map {
                ProtocolSelectedItemFactory.create(it)
            }
            beforeEach {
                reset(protocolClient)
            }

            it("should return internal server error when protocol select call fails with an exception") {
                `when`(protocolClient.select(org.mockito.kotlin.any())).thenReturn(
                    Calls.failure(IOException("Timeout"))
                )

                val response = invokeBppSelect(selectRequest.context, listItem)

                response shouldBeLeft BppError.Internal
            }

            it("should return  internal server error when protocol select call returns null body") {
                `when`(protocolClient.select(org.mockito.kotlin.any())).thenReturn(
                    Calls.response(
                        null
                    )
                )

                val response = invokeBppSelect(selectRequest.context, listItem)

                response shouldBeLeft BppError.NullResponse
                verify(protocolClient, times(1)).select(org.mockito.kotlin.any())
            }
      it("should return internal server error when bpp select call returns nack response body") {
        val context = contextFactory.create()
        `when`(protocolClient.select(org.mockito.kotlin.any())).thenReturn(
          Calls.response(ProtocolAckResponse(context, ResponseMessage.nack()))
        )
        val response = invokeBppSelect(selectRequest.context, listItem)

        response shouldBeLeft BppError.Nack
        verify(protocolClient, times(1)).select(org.mockito.kotlin.any())
      }
        }
    }

    private fun invokeBppSelect(
        context: ProtocolContext,
        list: List<ProtocolSelectedItem>
    ): Either<BppError, ProtocolAckResponse> {
        return protocolSelectService.select(
            context, //todo: a lot of places where the context factory is used but the action is wrong
            list
        )
    }

    private fun getSelectRequest() = ProtocolSelectRequest(
        contextFactory.create(),
        ProtocolSelectRequestMessage(
            order = ProtocolSelectMessageSelected(
                provider = ProtocolProvider(
                    id = "venugopala stores",
                    locations = listOf(ProtocolLocationFactory.idLocation(1))
                ),
                items = IdFactory.forItems(IdFactory.forProvider(1), 1)
                    .map { ProtocolSelectedItemFactory.create(it) }
            )
        )
    )
}
