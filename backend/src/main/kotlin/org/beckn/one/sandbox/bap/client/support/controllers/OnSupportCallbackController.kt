package org.beckn.one.sandbox.bap.client.support.controllers

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
class OnSupportCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnSupport>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnSupport>(store, loggingFactory, loggingService) {

  fun onSupport(@RequestBody supportResponse: ProtocolOnSupport) = onCallback(
    supportResponse,
      ProtocolContext.Action.ON_SUPPORT
    )
}