package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default
import com.fasterxml.jackson.annotation.JsonProperty

data class RatingDao @Default constructor(
  val value: Int? = null,
  val unit: String? = null,
  val max_value: Int? = null,
  val direction: DirectionDao? = null,
) {
  enum class DirectionDao {
    @JsonProperty("UP")
    UP,

    @JsonProperty("DOWN")
    DOWN
  }
}

