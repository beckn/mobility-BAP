package org.beckn.one.sandbox.bap.client.discovery.services

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import org.beckn.one.sandbox.bap.client.external.provider.BppClient
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.factories.SearchRequestFactory
import org.beckn.one.sandbox.bap.client.shared.dtos.SearchCriteria
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.common.factories.ContextFactoryInstance
import org.beckn.one.sandbox.bap.factories.UuidFactory
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolError
import org.beckn.protocol.schemas.ResponseMessage
import org.mockito.Mockito.*
import retrofit2.mock.Calls
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

internal class ProtocolSearchServiceSpec : DescribeSpec() {
  private val bppServiceClientFactory = mock(ProtocolClientFactory::class.java)
  private val clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"))
  private val uuidFactory = mock(UuidFactory::class.java)
  private val contextFactory = ContextFactoryInstance.create(uuidFactory, clock)
  private val bppSearchService = ProtocolSearchService(bppServiceClientFactory)
  private val bppServiceClient: BppClient = mock(BppClient::class.java)

  init {
    describe("Search Service") {
      `when`(uuidFactory.create()).thenReturn("9056ea1b-275d-4799-b0c8-25ae74b6bf51")
      `when`(bppServiceClientFactory.getClient(null)).thenReturn(bppServiceClient)
      val context = contextFactory.create()

      beforeEach {
        reset(bppServiceClient)
      }

      it("should return protocol internal server error when protocol search call fails with an exception") {
        val criteria =
          SearchCriteria(null,null,null,null,  "12.903561,77.5939631","12.9175403,77.5890075",null , null)

        `when`(bppServiceClient.search(org.mockito.kotlin.any())).thenReturn(
          Calls.failure(IOException("Timeout"))
        )

        val response = bppSearchService.search(context, criteria)

        response shouldBeLeft BppError.Internal
      }

      it("should return protocol server nack on error message") {
        val criteria =
          SearchCriteria(null,null,null,null,  "12.903561,77.5939631","12.9175403,77.5890075",null , null)

        `when`(bppServiceClient.search(org.mockito.kotlin.any())).thenReturn(
          Calls.response(ProtocolAckResponse(null, error = BppError.NullResponse.nullError, message = BppError.NullResponse.message()))
        )

        val response = bppSearchService.search(context, criteria)

        response shouldBeLeft BppError.Nack
      }

      it("should return null error on empty/null body") {
        val criteria =
          SearchCriteria(null,null,null,null,  "12.903561,77.5939631","12.9175403,77.5890075",null , null)

        `when`(bppServiceClient.search(org.mockito.kotlin.any())).thenReturn(
          Calls.response(null)
        )

        val response = bppSearchService.search(context, criteria)

        response shouldBeLeft BppError.NullResponse
      }
    }
  }

  private fun getSearchRequest(
    context: ProtocolContext,
    criteria: SearchCriteria
  ) = SearchRequestFactory.create(
    context = context,
    providerId = criteria.providerId,
    location = criteria.deliveryLocation,
    categoryId = criteria.categoryId
  )
}
