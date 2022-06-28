package org.beckn.one.sandbox.bap.client.discovery.mappers

import arrow.core.Either
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientSearchResponse
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientSearchResponseMessage
import org.beckn.one.sandbox.bap.client.shared.services.GenericOnPollMapper
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnSearch

class SearchClientResponseMapper(
  private val clientCatalogMapper: ClientCatalogMapper
) : GenericOnPollMapper<ProtocolOnSearch, ClientSearchResponse> {
  override fun transform(
    input: List<ProtocolOnSearch>,
    context: ProtocolContext
  ): Either<HttpError, ClientSearchResponse> =
    Either.Right(
      ClientSearchResponse(
        context = context,
        message = ClientSearchResponseMessage(
          catalogs = input.mapNotNull { response ->
            response.message?.catalog?.let(clientCatalogMapper::protocolToClientDto)?.copy(bppId = response.context?.bppId)?.copy(bppUri = response.context?.bppUri)
          })
      )
    )
}
