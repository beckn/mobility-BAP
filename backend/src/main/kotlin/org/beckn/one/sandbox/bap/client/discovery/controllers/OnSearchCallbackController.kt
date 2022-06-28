package org.beckn.one.sandbox.bap.client.discovery.controllers

import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractCallbackController
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class OnSearchCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnSearch>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnSearch>(store, loggingFactory, loggingService) {

  fun onSearch(@RequestBody searchResponse: ProtocolOnSearch) = onCallback(
      searchResponse,
      ProtocolContext.Action.ON_SEARCH
    )
}