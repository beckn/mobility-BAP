package org.beckn.one.sandbox.bap.client.order.quote.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.beckn.one.sandbox.bap.client.factories.CartFactory
import org.beckn.one.sandbox.bap.client.shared.dtos.CartDto
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientContext
import org.beckn.one.sandbox.bap.client.shared.dtos.GetQuoteRequestDto
import org.beckn.one.sandbox.bap.client.shared.dtos.GetQuoteRequestMessageDto
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.common.Verifier
import org.beckn.one.sandbox.bap.common.factories.MockNetwork
import org.beckn.one.sandbox.bap.common.factories.MockNetwork.retailBengaluruBpp
import org.beckn.one.sandbox.bap.common.factories.ResponseFactory
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.UuidFactory
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolError
import org.beckn.protocol.schemas.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = ["test"])
@TestPropertySource(locations = ["/application-test.yml"])
class GetQuoteControllerSpec @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val contextFactory: ContextFactory,
    val uuidFactory: UuidFactory,
) : DescribeSpec() {
  private val verifier: Verifier = Verifier(objectMapper)

  init {
    describe("Get Quote") {
      MockNetwork.startAllSubscribers()
      val context = ClientContext(transactionId = uuidFactory.create(),bppId = null, bppUri = null)
      val cart = CartFactory.create(bpp1Uri = retailBengaluruBpp.baseUrl())

      beforeEach {
        MockNetwork.resetAllSubscribers()
      }

      it("should return error when bpp select call fails") {
        retailBengaluruBpp.stubFor(post("/select").willReturn(serverError()))

        val getQuoteResponseString = invokeGetQuoteApi(context = context, cart = cart)
          .andExpect(status().isInternalServerError)
          .andReturn()
          .response.contentAsString

        verifyResponseMessage(getQuoteResponseString, ResponseMessage.nack(), BppError.Internal.error(), context)
      }

     it("should invoke provide select api and save message") {

        retailBengaluruBpp
          .stubFor(
            post("/select").willReturn(
              okJson(objectMapper.writeValueAsString(ResponseFactory.getDefault(contextFactory.create(transactionId = context.transactionId, action = ProtocolContext.Action.SELECT))))
            )
          )

        val getQuoteResponseString = invokeGetQuoteApi(context = context, cart = cart)
          .andExpect(status().is2xxSuccessful)
          .andReturn()
          .response.contentAsString

        verifyResponseMessage(
          getQuoteResponseString,
          ResponseMessage.ack(),
          expectedContext = context
        )
      }

      it("should return error when empty quotes v2 call fails") {
        retailBengaluruBpp.stubFor(post("/select").willReturn(serverError()))

        val getQuoteResponseString = invokeGetQuoteApiV2(listOf())
          .andExpect(status().is4xxClientError)
          .andReturn()
          .response.contentAsString

        val getQuoteResponse = objectMapper.readValue(getQuoteResponseString, object : TypeReference<List<ProtocolAckResponse>>(){})
        getQuoteResponse.first().context shouldNotBe null
        getQuoteResponse.first().message shouldBe  ResponseMessage.nack()
        getQuoteResponse.first().error shouldBe BppError.BadRequestError.error()

      }

      it("should invoke provide quote v2 api and save message") {
        retailBengaluruBpp
          .stubFor(
            post("/select").willReturn(
              okJson(objectMapper.writeValueAsString(ResponseFactory.getDefault(contextFactory.create(transactionId = context.transactionId, action = ProtocolContext.Action.SELECT))))
            )
          )

        val getQuoteResponseString = invokeGetQuoteApiV2(
          listOf(GetQuoteRequestDto(context = context,
          message = GetQuoteRequestMessageDto(cart = cart))))
          .andExpect(status().is2xxSuccessful)
          .andReturn()
          .response.contentAsString

       verifyResponseMessageV2(
          getQuoteResponseString,
          ResponseMessage.ack(),
          expectedContext = context
        )
      }
      it("should invoke  quote v2 api and throw error") {
        retailBengaluruBpp.stubFor(post("/select").willReturn(serverError()))

        val getQuoteResponseString = invokeGetQuoteApiV2(
          listOf(GetQuoteRequestDto(context = context,
            message = GetQuoteRequestMessageDto(cart = cart))))
          .andExpect(status().is2xxSuccessful)
          .andReturn()
          .response.contentAsString

        verifyResponseMessageV2(
          getQuoteResponseString,
          ResponseMessage.nack(),
          BppError.Internal.error(),
          expectedContext = context
        )
      }
    }
  }

  private fun verifyResponseMessage(
    getQuoteResponseString: String,
    expectedMessage: ResponseMessage,
    expectedError: ProtocolError? = null,
    expectedContext: ClientContext,
  ): ProtocolAckResponse {
    val getQuoteResponse = objectMapper.readValue(getQuoteResponseString, ProtocolAckResponse::class.java)
    getQuoteResponse.context shouldNotBe null
    getQuoteResponse.context?.messageId shouldNotBe null
    getQuoteResponse.context?.transactionId shouldBe expectedContext.transactionId
    getQuoteResponse.context?.action shouldBe ProtocolContext.Action.SELECT
    getQuoteResponse.message shouldBe expectedMessage
    getQuoteResponse.error shouldBe expectedError
    return getQuoteResponse
  }

  private fun invokeGetQuoteApi(context: ClientContext, cart: CartDto) = mockMvc
    .perform(
      MockMvcRequestBuilders.post("/client/v1/get_quote")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .content(
          objectMapper.writeValueAsString(
            GetQuoteRequestDto(context = context, message = GetQuoteRequestMessageDto(cart = cart))
          )
        )
    )

  private fun verifyResponseMessageV2(
    getQuoteResponseString: String,
    expectedMessage: ResponseMessage,
    expectedError: ProtocolError? = null,
    expectedContext: ClientContext,
  ): List<ProtocolAckResponse> {
    val getQuoteResponse = objectMapper.readValue(getQuoteResponseString, object : TypeReference<List<ProtocolAckResponse>>(){})
    getQuoteResponse.first().context shouldNotBe null
    getQuoteResponse.first().context?.messageId shouldNotBe null
    getQuoteResponse.first().context?.transactionId shouldBe expectedContext.transactionId
    getQuoteResponse.first().context?.action shouldBe ProtocolContext.Action.SELECT
    getQuoteResponse.first().message shouldBe expectedMessage
    getQuoteResponse.first().error shouldBe expectedError
    return getQuoteResponse
  }

  private fun invokeGetQuoteApiV2(getQuoteRequestDtoList: List<GetQuoteRequestDto>) = mockMvc
    .perform(
      MockMvcRequestBuilders.post("/client/v2/get_quote")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .content(
          objectMapper.writeValueAsString(
            getQuoteRequestDtoList
          )
        )
    )
}
