package org.beckn.one.sandbox.bap.client.order.quote.controllers

import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractCallbackController
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnInit
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.beckn.protocol.schemas.ProtocolOnSelect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class OnGetQuoteCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnSelect>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnSelect>(store, loggingFactory, loggingService) {

  fun onQuoteSelect(@RequestBody selectResponse: ProtocolOnSelect) = onCallback(
      selectResponse,
      ProtocolContext.Action.ON_SELECT
    )
}