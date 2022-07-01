package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class LanguageDao @Default constructor(
  val code: String? = null,
)
