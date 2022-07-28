package org.beckn.one.sandbox.bap.client.discovery.controllers

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.errors.database.DatabaseError
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.entities.*
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
@TestPropertySource(locations = ["/application-test.yml"])
internal class OnSearchCallbackControllerSpec @Autowired constructor(
  private val contextFactory: ContextFactory,
  private val loggingFactory: LoggingFactory,
  private val loggingService: LoggingService
) : DescribeSpec() {

  private val fixedClock = Clock.fixed(
    Instant.parse("2018-11-30T18:35:24.00Z"),
    ZoneId.of("UTC")
  )

  private val entityContext = ProtocolContext(
    domain = "LocalRetail",
    country = "IN",
    action = ProtocolContext.Action.ON_SEARCH,
    city = "Pune",
    coreVersion = "0.9.1-draft03",
    bapId = "http://host.bap.com",
    bapUri = "http://host.bap.com/v1",
    bppId = "http://host.bpp.com",
    bppUri = "http://host.bpp.com/v1",
    transactionId = "222",
    messageId = "222",
    timestamp = OffsetDateTime.now(fixedClock)
  )


  init {
    describe("OnSearchCallbackController") {
      context("should throw Database saving error") {
        val mockOnPollService = mock<ProtocolResponseStorageService<ProtocolOnSearch>> {
          onGeneric { save(any()) }.thenReturn(Either.Left(DatabaseError.OnWrite))
        }
        val onSearchCallbackController = OnSearchCallbackController(mockOnPollService, loggingFactory, loggingService, contextFactory)
        it("should respond with database write error ") {
          val response = onSearchCallbackController.onSearch(entitySearchResults())
          response.statusCode shouldBe DatabaseError.OnWrite.status()
        }
      }
      context("should save response successfully") {
        val mockOnPollService = mock<ProtocolResponseStorageService<ProtocolOnSearch>> {
          onGeneric { save(any()) }.thenReturn(Either.Right(entitySearchResults()))
        }
        val onSearchCallbackController = OnSearchCallbackController(mockOnPollService, loggingFactory, loggingService, contextFactory)
        it("should respond with successful ACK ") {
          val response = onSearchCallbackController.onSearch(entitySearchResults())
          response.statusCode shouldBe HttpStatus.OK
        }
      }
    }
  }

  fun entitySearchResults(): ProtocolOnSearch{
    return ProtocolOnSearch(
      context = entityContext,
      message = ProtocolOnSearchMessage(ProtocolCatalog()),
      error = null,
    )
  }
}