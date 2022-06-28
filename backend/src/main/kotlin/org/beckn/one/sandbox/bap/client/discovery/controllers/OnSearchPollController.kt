package org.beckn.one.sandbox.bap.client.discovery.controllers

import org.beckn.one.sandbox.bap.client.shared.controllers.AbstractClientOnPollController
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientSearchResponse
import org.beckn.one.sandbox.bap.client.shared.services.GenericClientOnPollService
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OnSearchPollController @Autowired constructor(
  val contextFactory: ContextFactory,
  pollService: GenericClientOnPollService<ProtocolOnSearch, ClientSearchResponse>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService,
 ):AbstractClientOnPollController<ProtocolOnSearch, ClientSearchResponse>(pollService, contextFactory, loggingFactory, loggingService) {

  private val log: Logger = LoggerFactory.getLogger(this::class.java)

  @GetMapping("/client/v1/on_search")
  @ResponseBody
  fun onSearchV1(@RequestParam messageId: String,
                 @RequestParam providerName : String?,
                 @RequestParam categoryName: String?): ResponseEntity<out ClientResponse> {
    log.info("on Search for client layer : $messageId, $providerName, $categoryName")
   return onPoll(
       contextFactory.create(messageId= messageId),
       providerName, categoryName,null,
       ProtocolContext.Action.ON_SEARCH
    )
  }
}