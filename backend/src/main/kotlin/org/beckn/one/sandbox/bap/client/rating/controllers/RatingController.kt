package org.beckn.one.sandbox.bap.client.rating.controllers

import org.beckn.one.sandbox.bap.client.rating.services.RatingService
import org.beckn.one.sandbox.bap.client.shared.dtos.RatingRequestDto
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ResponseMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RatingController @Autowired constructor(
    private val contextFactory: ContextFactory,
    private val ratingService: RatingService
) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)

  @PostMapping("/client/v1/rating")
  @ResponseBody
  fun getRatingV1(
    @RequestBody ratingRequest: RatingRequestDto,
  ): ResponseEntity<ProtocolAckResponse> {
    val context = getContext(transactionId = ratingRequest.context.transactionId, bppId = ratingRequest.context.bppId, bppUri = ratingRequest.context.bppUri)
    return ratingService.rating(context = context, request = ratingRequest)
      .fold(
        {
          log.error("Error when rating refId: {}", it)
          mapToErrorResponse(it, null)
        },
        {
          log.info("Successfully rated refId. Message: {}", it)
          ResponseEntity.ok(it)
        }
      )
  }

  private fun mapToErrorResponse(it: HttpError, context: ProtocolContext?) = ResponseEntity
    .status(it.status())
    .body(
      ProtocolAckResponse(
        context = context,
        message = it.message(),
        error = it.error()
      )
    )

  private fun getContext(transactionId: String,bppId: String? = null, bppUri: String?= null) =
    contextFactory.create(action = ProtocolContext.Action.RATING, transactionId = transactionId, bppUri = bppUri, bppId = bppId)
}