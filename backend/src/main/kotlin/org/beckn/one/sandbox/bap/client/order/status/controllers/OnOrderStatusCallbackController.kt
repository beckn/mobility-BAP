package org.beckn.one.sandbox.bap.client.order.status.controllers

import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractCallbackController
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class OnOrderStatusCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnOrderStatus>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnOrderStatus>(store, loggingFactory, loggingService) {

  fun onOrderStatus(@RequestBody statusResponse: ProtocolOnOrderStatus) = onCallback(
    statusResponse,
      ProtocolContext.Action.ON_STATUS
    )
}