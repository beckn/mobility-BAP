package org.beckn.one.sandbox.bap.client.shared.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import org.beckn.one.sandbox.bap.client.external.bap.ProtocolClient
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientCatalog
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientSearchResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientSearchResponseMessage
import org.beckn.one.sandbox.bap.common.factories.MockProtocolBap
import org.beckn.protocol.schemas.ProtocolCatalog
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.beckn.protocol.schemas.ProtocolOnSearchMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@SpringBootTest
@ActiveProfiles(value = ["test"])
@TestPropertySource(locations = ["/application-test.yml"])
internal class GenericOnPollServiceSpec @Autowired constructor(
  private val onSearchPollService: GenericOnPollService<ProtocolOnSearch, ClientSearchResponse>,
  private val protocolClient: ProtocolClient,
  private val mapper: ObjectMapper
) : DescribeSpec() {
  private val fixedClock = Clock.fixed(
    Instant.parse("2018-11-30T18:35:24.00Z"),
    ZoneId.of("UTC")
  )

  private val context = ProtocolContext(
    domain = "LocalRetail",
    country = "IN",
    action = ProtocolContext.Action.SEARCH,
    city = "Pune",
    coreVersion = "0.9.1-draft03",
    bapId = "http://host.bap.com",
    bapUri = "http://host.bap.com",
    transactionId = "222",
    messageId = "222",
    timestamp = OffsetDateTime.now(fixedClock)
  )

  init {
    describe("GenericOnReplyService") {
      val mockProtocolBap = MockProtocolBap.withResetInstance()
      mockProtocolBap.stubFor(
        WireMock.get("/protocol/response/v1/on_search?messageId=${context.messageId}").willReturn(WireMock.okJson(mapper.writeValueAsString(entitySearchResults())))
      )

      it("should return search results for given message id in context") {
        val response = onSearchPollService.onPoll(context, protocolClient.getSearchResponsesCall(context.messageId))
        response.shouldBeRight(
          ClientSearchResponse(
            context = context,
            message = ClientSearchResponseMessage(
              catalogs = listOf(ClientCatalog(), ClientCatalog())
            )
          )
        )
      }
    }
  }

  fun entitySearchResults(): List<ProtocolOnSearch> {
    val entitySearchResponse = ProtocolOnSearch(
      context = context,
      message = ProtocolOnSearchMessage(ProtocolCatalog())
    )
    return listOf(
      entitySearchResponse,
      entitySearchResponse,
    )
  }

}