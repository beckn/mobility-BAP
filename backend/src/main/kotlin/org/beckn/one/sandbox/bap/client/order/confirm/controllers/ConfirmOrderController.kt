package org.beckn.one.sandbox.bap.client.order.confirm.controllers

import com.google.gson.GsonBuilder
import org.beckn.one.sandbox.bap.auth.utils.SecurityUtil
import org.beckn.one.sandbox.bap.client.order.confirm.services.ConfirmOrderService
import org.beckn.one.sandbox.bap.client.shared.Util
import org.beckn.one.sandbox.bap.client.shared.dtos.OrderRequestDto
import org.beckn.one.sandbox.bap.client.shared.dtos.OrderResponse
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.message.entities.OrderDao
import org.beckn.one.sandbox.bap.message.services.ResponseStorageService
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ResponseMessage
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ConfirmOrderController @Autowired constructor(
  private val contextFactory: ContextFactory,
  private val confirmOrderService: ConfirmOrderService,
  private val confirmOrderRepository: ResponseStorageService<OrderResponse, OrderDao>
) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)

  @PostMapping("/client/v1/confirm_order")
  @ResponseBody
  fun confirmOrderV1(
    @RequestBody orderRequest: OrderRequestDto
  ): ResponseEntity<ProtocolAckResponse> {
    val context = getContext(orderRequest.context.transactionId, bppId = orderRequest.context.bppId, bppUri =  orderRequest.context.bppUri)
    log.info("Context from request : {}", context)
    return confirmOrderService.confirmOrder(
      context = context,
      order = orderRequest.message
    ).fold(
        {
          log.error("Error when confirming order: {}", it)
          mapToErrorResponseV1(it, null)
        },
        {
          log.info("Successfully confirmed order. Message: {}", it)
          ResponseEntity.ok(it ?: ProtocolAckResponse(context = context, message = ResponseMessage.ack()))
        }
      )
  }

  private fun mapToErrorResponseV1(it: HttpError, context: ProtocolContext?) = ResponseEntity
    .status(it.status())
    .body(
      ProtocolAckResponse(
        context = context,
        message = it.message(),
        error = it.error()
      )
    )



  @PostMapping("/client/v2/confirm_order")
  @ResponseBody
  fun confirmOrderV2(
    @RequestBody orderRequest: List<OrderRequestDto>
  ): ResponseEntity<List<ProtocolAckResponse>> {
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    log.info(" Order request from client : {}", gsonPretty.toJson(orderRequest))
    var okResponseConfirmOrders: MutableList<ProtocolAckResponse> = ArrayList()
    if (!orderRequest.isNullOrEmpty()) {
//      if (SecurityUtil.getSecuredUserDetail() != null) {
        val parentOrderId = Util.getRandomString()
        for (order in orderRequest) {
          val context = getContext(order.context.transactionId, bppUri = order.context.bppUri, bppId = order.context.bppId)
          confirmOrderService.confirmOrder(
            context = context,
            order = order.message
          ).fold(
              {apiError->
                log.error("Error when confirming order: {}", apiError)
                okResponseConfirmOrders.add(
                  ProtocolAckResponse(
                    context = null,
                    message = apiError.message(),
                    error = apiError.error()
                  )
                )
              },
              { apiResult ->
                log.info("Successfully confirmed order. Message: {}", apiResult)
                val resultContext = apiResult?.context
                confirmOrderRepository.updateDocByQuery(
                  OrderDao::messageId eq resultContext?.messageId,
                  OrderDao(
                    userId = SecurityUtil.getSecuredUserDetail()?.uid,
                    messageId = resultContext?.messageId ,
                    transactionId = null,
                    parentOrderId =  parentOrderId
                  )
                ).fold(
                  { updateError->
                  log.error("Error when updating order: {}", updateError)
                    okResponseConfirmOrders.add(
                      ProtocolAckResponse(
                        context = null,
                        message = updateError.message(),
                        error = updateError.error()
                      )
                    )
                  },
                  { updateResult->
                    log.info("Successfully updated  order in client layer db : {}", updateResult)
                    okResponseConfirmOrders.add(ProtocolAckResponse(context = resultContext, message = ResponseMessage.ack()))
                  }
                )
              }
            )
        }
        return ResponseEntity.ok(okResponseConfirmOrders)
//      } else {
//        return mapToErrorResponseV2(BppError.AuthenticationError, null)
//      }
    } else {
      return mapToErrorResponseV2(BppError.BadRequestError, null)
    }
  }

  private fun mapToErrorResponseV2(it: HttpError, context: ProtocolContext?) = ResponseEntity
    .status(it.status())
    .body(
      listOf(
        ProtocolAckResponse(
          context = context,
          message = it.message(),
          error = it.error()
        )
      )
    )

  private fun getContext(transactionId: String, bppId: String? = null, bppUri: String?= null) =
    contextFactory.create(action = ProtocolContext.Action.CONFIRM, transactionId = transactionId, bppUri = bppUri, bppId = bppId)
}