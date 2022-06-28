package org.beckn.one.sandbox.bap.client.order.confirm.controllers

import org.beckn.one.sandbox.bap.auth.utils.SecurityUtil
import org.beckn.one.sandbox.bap.client.external.bap.ProtocolClient
import org.beckn.one.sandbox.bap.client.order.confirm.services.OnConfirmOrderService
import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractClientOnPollController
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientConfirmResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientResponse
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.one.sandbox.bap.client.shared.services.GenericClientOnPollService
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.one.sandbox.bap.errors.database.DatabaseError
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.entities.OrderDao
import org.beckn.one.sandbox.bap.message.mappers.OnOrderProtocolToEntityOrder
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnConfirm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OnConfirmOrderController @Autowired constructor(
  onPollService: GenericClientOnPollService<ProtocolOnConfirm, ClientConfirmResponse>,
  val contextFactory: ContextFactory,
  val protocolClient: ProtocolClient,
  val mapping: OnOrderProtocolToEntityOrder,
  val onConfirmOrderService: OnConfirmOrderService,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
) : AbstractClientOnPollController<ProtocolOnConfirm, ClientConfirmResponse>(onPollService, contextFactory, loggingFactory, loggingService) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)

  @RequestMapping("/client/v1/on_confirm_order")
  @ResponseBody
  fun onConfirmOrderV1(
    @RequestParam messageId: String
  ): ResponseEntity<out ClientResponse> = onPoll(
    contextFactory.create(messageId= messageId),null, null, null,
    ProtocolContext.Action.ON_CONFIRM
  )

  @RequestMapping("/client/v2/on_confirm_order")
  @ResponseBody
  fun onConfirmOrderV2(
    @RequestParam messageIds: String
  ): ResponseEntity<out List<ClientResponse>> {
    val user = SecurityUtil.getSecuredUserDetail()
    if (user != null) {
      if (messageIds.isNotEmpty() && messageIds.trim().isNotEmpty()) {
        val messageIdArray = messageIds.split(",")
        var okResponseConfirmOrder: MutableList<ClientConfirmResponse> = ArrayList()
          for (messageId in messageIdArray) {
            val bapResult = onPoll(
              contextFactory.create(messageId= messageId),null, null, null,
              ProtocolContext.Action.ON_CONFIRM
            )
            when (bapResult.statusCode.value()) {
              200 -> {
                val resultResponse: ClientConfirmResponse = bapResult.body as ClientConfirmResponse
                if (resultResponse.message?.order != null) {
                  onConfirmOrderService.findById(resultResponse.context?.messageId).fold(
                    {
                      log.error("Db error to fetch order based on message id")
                      okResponseConfirmOrder.add(
                        ClientConfirmResponse(
                          error = it.error(),
                          context = contextFactory.create(messageId = messageId,action = ProtocolContext.Action.ON_CONFIRM)
                        )
                      )
                    },{
                      val orderDao: OrderDao = mapping.protocolToEntity(resultResponse.message.order!!)
                      orderDao.transactionId = resultResponse.context?.transactionId
                      orderDao.userId = user.uid
                      orderDao.messageId = resultResponse.context?.messageId
                      orderDao.parentOrderId = it.parentOrderId
                      onConfirmOrderService.updateOrder(orderDao).fold(
                        {
                          okResponseConfirmOrder.add(
                            ClientConfirmResponse(
                              error = it.error(),
                              context = contextFactory.create(messageId = messageId,action = ProtocolContext.Action.ON_CONFIRM)
                            )
                          )
                        }, {
                          resultResponse.parentOrderId = orderDao.parentOrderId
                          okResponseConfirmOrder.add(resultResponse)
                        }
                      )

                    }
                  )
                } else {
                  okResponseConfirmOrder.add(
                    ClientConfirmResponse(
                      error = DatabaseError.NoDataFound.noDataFoundError,
                      context = contextFactory.create(messageId = messageId,action = ProtocolContext.Action.ON_CONFIRM)
                    )
                  )
                }
              }
              else -> {
                okResponseConfirmOrder.add(
                  ClientConfirmResponse(
                    error = bapResult.body?.error,
                    context = contextFactory.create(messageId = messageId ,action = ProtocolContext.Action.ON_CONFIRM)
                  )
                )
              }
            }
          }
          log.info("`Initiated and returning onConfirm acknowledgment`. Message: {}", okResponseConfirmOrder)

          return ResponseEntity.ok(okResponseConfirmOrder)
      } else {
        return mapToErrorResponse(BppError.BadRequestError)
      }
    } else {
      return mapToErrorResponse(
        BppError.AuthenticationError
      )
    }
  }

  private fun mapToErrorResponse(it: HttpError, context: ProtocolContext? = null) = ResponseEntity
    .status(it.status())
    .body(
      listOf(
        ClientConfirmResponse(
          context = context,
          error = it.error()
        )
      )
    )
}