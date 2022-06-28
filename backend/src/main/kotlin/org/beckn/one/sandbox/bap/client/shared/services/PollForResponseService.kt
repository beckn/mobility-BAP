package org.beckn.one.sandbox.bap.client.shared.services

import arrow.core.Either
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.one.sandbox.bap.message.services.MessageService
import org.beckn.one.sandbox.bap.message.services.ProtocolResponseStorageService
import org.beckn.protocol.schemas.ProtocolResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class PollForResponseService<Protocol: ProtocolResponse> constructor(
  private val responseStorageService: ProtocolResponseStorageService<Protocol>
) {
  private val log: Logger = LoggerFactory.getLogger(this::class.java)

  open fun findResponses(messageId: String): Either<HttpError, List<Protocol>> {
    log.info("Got fetch request for message id: {}", messageId)
    return responseStorageService.findByMessageId(messageId)
  }

  open fun findResponsesByOrderId(messageId: String): Either<HttpError, List<Protocol>> {
    log.info("Got fetch request for order id: {}", messageId)
    return responseStorageService.findByOrderId(messageId)
  }

  open fun findSearchCatalog(messageId: String, providerName: String?, categoryName: String?): Either<HttpError, List<Protocol>> {
    log.info("Got fetch request for search catalog: {}, $messageId, $providerName, $categoryName")
    return responseStorageService.findSearchCatalog(messageId, providerName, categoryName)
  }

}