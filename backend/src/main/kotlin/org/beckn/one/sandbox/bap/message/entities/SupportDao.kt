package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class SupportDao @Default constructor(
  val type: SupportTypeDao? = null,
  val ref_id: List<java.time.OffsetDateTime>? = null,
  val channels:Map<String, String>? = null,
) {
  enum class SupportTypeDao(val value: String) {
    ORDER("order"),
    BILLING("billing"),
    FULFILLMENT("fulfillment")
  }
}