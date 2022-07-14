package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolItem @Default constructor(
  val id: String? = null,
  val parentItemId: String? = null,
  val fulfillmentId: String? = null,
  val descriptor: ProtocolDescriptor? = null,
  val price: ProtocolPrice? = null,
  val categoryId: String? = null,
  val locationId: String? = null,
  val time: ProtocolTime? = null,
  val matched: Boolean? = null,
  val related: Boolean? = null,
  val recommended: Boolean?,
  val tags: Map<String, String>? = null,
  val rating: Int? = null,
  val rateable: Boolean? = null,
  )

