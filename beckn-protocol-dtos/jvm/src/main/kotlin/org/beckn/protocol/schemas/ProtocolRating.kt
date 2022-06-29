package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolRating @Default constructor(
  val value: Int? = null,
  val unit: String? = null,
  val max_value: Int? = null,
  val direction: ProtocolDirection? = null,
){
  enum class ProtocolDirection {
    @JsonProperty("UP")
    UP,
    @JsonProperty("DOWN")
    DOWN
  }
}
