package org.beckn.one.sandbox.bap.client.rating.controllers

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
class OnRatingCallbackController @Autowired constructor(
  store: ProtocolResponseStorageService<ProtocolOnRating>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
  val contextFactory: ContextFactory
): AbstractCallbackController<ProtocolOnRating>(store, loggingFactory, loggingService) {

  fun onRating(@RequestBody ratingResponse: ProtocolOnRating) = onCallback(
    ratingResponse,
      ProtocolContext.Action.ON_RATING
    )
}