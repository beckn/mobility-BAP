package org.beckn.one.sandbox.bap.client.order.cancel.controllers

import org.beckn.one.sandbox.bap.client.external.bap.ProtocolClient
import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractClientOnPollController
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientCancelResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientResponse
import org.beckn.one.sandbox.bap.client.shared.services.GenericClientOnPollService
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnCancel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OnCancelOrderController @Autowired constructor(
    onPollService: GenericClientOnPollService<ProtocolOnCancel, ClientCancelResponse>,
    val contextFactory: ContextFactory,
    val protocolClient: ProtocolClient,
    loggingFactory: LoggingFactory,
    loggingService: LoggingService,
) : AbstractClientOnPollController<ProtocolOnCancel, ClientCancelResponse>(onPollService, contextFactory, loggingFactory, loggingService) {

  @RequestMapping("/client/v1/on_cancel_order")
  @ResponseBody
  fun onCancelOrderV1(
    @RequestParam messageId: String
  ): ResponseEntity<out ClientResponse> = onPoll(
      contextFactory.create(messageId= messageId),null, null, null,
      ProtocolContext.Action.ON_CANCEL
  )
}