package org.beckn.protocol.schemas

data class ProtocolAuthorization(
  val type: String? = null,
  val token: String? = null,
  val validFrom: java.time.OffsetDateTime? = null,
  val validTo: java.time.OffsetDateTime? = null,
  val status: String? = null,
)
