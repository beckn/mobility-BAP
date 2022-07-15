package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class DocumentDao @Default constructor(
  val url: String? = null,
  val label: String? = null,
)