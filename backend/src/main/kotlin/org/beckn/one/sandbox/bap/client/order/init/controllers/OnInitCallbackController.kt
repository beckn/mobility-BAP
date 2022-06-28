package org.beckn.one.sandbox.bap.client.order.init.controllers

import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractCallbackController
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnInit
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class OnInitCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnInit>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnInit>(store, loggingFactory, loggingService) {

  fun onInitOrder(@RequestBody initResponse: ProtocolOnInit) = onCallback(
    initResponse,
      ProtocolContext.Action.ON_INIT
    )
}