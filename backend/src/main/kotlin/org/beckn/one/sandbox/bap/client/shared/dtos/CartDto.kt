package org.beckn.one.sandbox.bap.client.shared.dtos

import org.beckn.protocol.schemas.Default
import org.beckn.protocol.schemas.ProtocolQuotation
import org.beckn.protocol.schemas.ProtocolScalar
import org.beckn.protocol.schemas.ProtocolSelectedItem

data class GetQuoteRequestDto @Default constructor(
  val context: ClientContext,
  val message: GetQuoteRequestMessageDto,
)

data class GetQuoteRequestMessageDto @Default constructor(
  val cart: CartDto
)

data class CartDto @Default constructor(
//  val items: List<CartItemDto>? = null
  val items: List<ProtocolSelectedItem>? = null
)

data class CartItemDto @Default constructor(
  val id: String,
  val fulfillmentId: String,
  val quantity: CartSelectedItemQuantity ?= null,
  val bppId: String ?= null,
  val provider: CartItemProviderDto ?= null,
  val quote: ProtocolQuotation? = null,
)

data class CartSelectedItemQuantity @Default constructor(
  val count: Int,
  val measure: ProtocolScalar? = null
)

data class CartItemProviderDto @Default constructor(
  val id: String,
  val locations: List<String>? = null
)
