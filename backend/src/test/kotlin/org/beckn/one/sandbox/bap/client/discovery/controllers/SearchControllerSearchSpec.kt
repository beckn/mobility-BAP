package org.beckn.one.sandbox.bap.client.discovery.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.beckn.one.sandbox.bap.client.discovery.services.ProtocolSearchService
import org.beckn.one.sandbox.bap.client.external.provider.BppClient
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.factories.SearchRequestFactory
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientContext
import org.beckn.one.sandbox.bap.client.shared.dtos.SearchCriteria
import org.beckn.one.sandbox.bap.client.shared.dtos.SearchRequestDto
import org.beckn.one.sandbox.bap.client.shared.dtos.SearchRequestMessageDto
import org.beckn.one.sandbox.bap.common.factories.MockNetwork
import org.beckn.one.sandbox.bap.common.factories.MockNetwork.retailBengaluruBpp
import org.beckn.one.sandbox.bap.common.factories.ResponseFactory
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.UuidFactory
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ResponseStatus.ACK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = ["test"])
@TestPropertySource(locations = ["/application-test.yml"])
class SearchControllerSpec @Autowired constructor(
  val mockMvc: MockMvc,
  val objectMapper: ObjectMapper,
  val contextFactory: ContextFactory,
  val uuidFactory: UuidFactory,

) : DescribeSpec() {
  init {

    describe("Search") {

      MockNetwork.startAllSubscribers()
      beforeEach {
        MockNetwork.resetAllSubscribers()
      }

        it("should return error response when protocol server api fails") {
          retailBengaluruBpp
            .stubFor(
              post("/search").willReturn(serverError()))

          invokeSearchApi()
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.message.ack.status", `is`("NACK")))
            .andExpect(jsonPath("$.error.code", `is`("BAP_011")))
            .andExpect(jsonPath("$.error.message", `is`("BPP returned error")))
        }

      it("should invoke search and return success message") {
        stubProtocolSearchApi()
        val result = invokeSearchApi().andExpect(status().is2xxSuccessful)
          .andExpect(jsonPath("$.message.ack.status", `is`(ACK.status)))
          .andExpect(jsonPath("$.context.message_id", `is`(notNullValue())))
          .andReturn()

        verifyThatSearchApiWasInvoked()
        verifyAckResponse(result)
      }
    }
  }

  private fun stubProtocolSearchApi() {
    retailBengaluruBpp
      .stubFor(
        post("/search")
          .willReturn(okJson(objectMapper.writeValueAsString(ResponseFactory.getDefault(contextFactory.create()))))
      )
  }


  private fun verifyAckResponse(result: MvcResult): ProtocolAckResponse {
    val searchResponse = objectMapper.readValue(result.response.contentAsString, ProtocolAckResponse::class.java)
    searchResponse.message.ack.status shouldBe ACK
    return searchResponse
  }

  private fun verifyThatSearchApiWasInvoked() {
    retailBengaluruBpp.verify(postRequestedFor(urlEqualTo("/search")))
  }

  private fun invokeSearchApi(location: String = "", providerId: String = "", bppId: String = "", bppUri: String = ""): ResultActions {
    val searchRequestDto = SearchRequestDto(
      context = ClientContext(transactionId = uuidFactory.create(), bppId = bppId,bppUri),
      message = SearchRequestMessageDto(
        criteria = SearchCriteria(
          searchString = "Fictional mystery books",
          deliveryLocation = location,
          providerId = providerId,
          pickupLocation = location,
          dropLocation = location
        )
      )
    )
    return mockMvc
      .perform(
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/client/v1/search")
          .content(objectMapper.writeValueAsString(searchRequestDto))
          .contentType(MediaType.APPLICATION_JSON)
      )
  }
}
