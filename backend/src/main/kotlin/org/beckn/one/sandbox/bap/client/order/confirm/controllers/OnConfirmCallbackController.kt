package org.beckn.one.sandbox.bap.client.order.confirm.controllers

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
class OnConfirmCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnConfirm>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnConfirm>(store, loggingFactory, loggingService) {

  fun onConfirmOrder(@RequestBody confirmResponse: ProtocolOnConfirm) = onCallback(
    confirmResponse,
      ProtocolContext.Action.ON_CONFIRM
    )
}