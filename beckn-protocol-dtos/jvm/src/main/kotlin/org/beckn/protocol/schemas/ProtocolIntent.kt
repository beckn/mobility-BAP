package org.beckn.protocol.schemas

data class ProtocolIntent (
  val fulfillment: ProtocolFulfillment?= null,
  val item: ProtocolIntentItem? = null,
  val provider: ProtocolProvider? = null,
  val category: ProtocolCategory? = null,
  val tags: Map<String, String>? = null,
  val payment: ProtocolPayment? = null,
  val offer: ProtocolOffer? = null,
  val descriptor: ProtocolDescriptor? = null,
)

data class ProtocolIntentItem(
  val id: String? = null,
  val descriptor: ProtocolIntentItemDescriptor? = null
)

data class ProtocolIntentItemDescriptor(
  val name: String? = null,
  val tags: Map<String, String>? = null
)