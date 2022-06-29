package org.beckn.protocol.schemas

data class ProtocolSupport @Default constructor(
  val type: ProtocolSupportType? = null,
  val ref_id: List<java.time.OffsetDateTime>? = null,
  val channels:Map<String, String>? = null,
) {
  enum class ProtocolSupportType(val value: String) {
    ORDER("order"),
    BILLING("billing"),
    FULFILLMENT("fulfillment")
  }
}
