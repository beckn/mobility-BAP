package org.beckn.protocol.schemas

data class ProtocolDimensions(
  val length: ProtocolScalar? = null,
  val breadth: ProtocolScalar? = null,
  val height: ProtocolScalar? = null,
)