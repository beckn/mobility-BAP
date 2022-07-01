package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class ProviderDao @Default constructor(
  val id: String? = null,
  val descriptor: DescriptorDao? = null,
  val time: TimeDao? = null,
  val locations: List<LocationDao>? = null,
  val tags: Map<String, String>? = null,
  val category_id : String? = null,
  val rating: Int? = null,
  val categories: List<CategoryDao>? = null,
  val fulfillments: List<FulfillmentDao>? = null,
  val payments: List<PaymentDao>? = null,
  val offers: List<OfferDao>? = null,
  val items: List<ItemDao>? = null,
  val exp: java.time.OffsetDateTime? = null,
)
