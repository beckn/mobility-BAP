package org.beckn.one.sandbox.bap.client.order.cancel.controllers

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
class OnCancelCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnCancel>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnCancel>(store, loggingFactory, loggingService) {

  fun onCancel(@RequestBody cancelResponse: ProtocolOnCancel) = onCallback(
    cancelResponse,
      ProtocolContext.Action.ON_CANCEL
    )
}