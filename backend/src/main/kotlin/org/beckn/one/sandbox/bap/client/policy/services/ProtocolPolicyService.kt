package org.beckn.one.sandbox.bap.client.policy.services

import arrow.core.Either
import org.beckn.one.sandbox.bap.client.external.hasBody
import org.beckn.one.sandbox.bap.client.external.isAckNegative
import org.beckn.one.sandbox.bap.client.external.isInternalServerError
import org.beckn.one.sandbox.bap.client.external.provider.ProtocolClientFactory
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.protocol.schemas.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class ProtocolPolicyService @Autowired constructor(
  private val bppServiceClientFactory: ProtocolClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(ProtocolPolicyService::class.java)

  fun getCancellationReasons(context: ProtocolContext): Either<BppError, ProtocolAckResponse> =
    Either.catch {
      log.info("Invoking get cancellation reasons API on Protocol Server: {}")
      val bppServiceClient = bppServiceClientFactory.getClient(null)
      val httpResponse = bppServiceClient.getCancellationReasons(
        ProtocolGetPolicyRequest(
          context = context
        )
      ).execute()
      log.info("Protocol Server get cancellation reasons response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())

      return when {
        httpResponse.isInternalServerError() -> Either.Left(BppError.Internal)
        !httpResponse.hasBody() -> Either.Left(BppError.NullResponse)
        httpResponse.isAckNegative() -> Either.Left(BppError.Nack)
        else -> Either.Right(httpResponse.body()!!)
      }
    }.mapLeft {
      log.error("Error when invoking Protocol Server get cancellation reasons API", it)
      BppError.Internal
    }

  fun getRatingCategories(context: ProtocolContext): Either<BppError, List<ProtocolRatingCategory>> =
    Either.catch {
      log.info("Invoking get rating categories API on Protocol Server: {}")
      val bppServiceClient = bppServiceClientFactory.getClient(null)
      val httpResponse = bppServiceClient.getRatingCategories(
        ProtocolGetPolicyRequest(
          context = context
        )
      ).execute()
      log.info("Protocol Server get rating categories response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
      return when{
        httpResponse.isInternalServerError() -> Either.Left(BppError.Internal)
        !httpResponse.hasBody() || hasEmptyBody(httpResponse) -> Either.Left(BppError.NullResponse)
        else -> Either.Right(httpResponse.body()!!)
      }
    }.mapLeft {
      log.error("Error when invoking Protocol Server get rating categories reasons API", it)
      BppError.Internal
    }

  private fun <T> hasEmptyBody(httpResponse: Response<List<T>>) =
    httpResponse.body()!!.isEmpty()

}
