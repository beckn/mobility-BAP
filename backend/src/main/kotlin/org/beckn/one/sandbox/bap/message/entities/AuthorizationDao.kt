package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class AuthorizationDao @Default constructor(
  val type: String? = null,
  val token: String? = null,
  val validFrom: java.time.OffsetDateTime? = null,
  val validTo: java.time.OffsetDateTime? = null,
  val status: String? = null,
)