package org.beckn.protocol.schemas

data class ProtocolProvider @Default constructor(
  val id: String? = null,
  val descriptor: ProtocolDescriptor? = null,
  val time: ProtocolTime? = null,
  val locations: List<ProtocolLocation>? = null,
  val tags: Map<String, String>? = null,
  val category_id : String? = null,
  val rating: Int? = null,
  val categories: List<ProtocolCategory>? = null,
  val fulfillments: List<ProtocolFulfillment>? = null,
  val payments: List<ProtocolPayment>? = null,
  val offers: List<ProtocolOffer>? = null,
  val items: List<ProtocolItem>? = null,
  val exp: java.time.OffsetDateTime? = null,
)
