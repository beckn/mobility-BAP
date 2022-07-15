package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default
import com.fasterxml.jackson.annotation.JsonProperty

data class RatingDao @Default constructor(
  val value: Int? = null,
  val unit: String? = null,
  val max_value: Int? = null,
  val direction: DirectionDao? = null,
  val ratingCategory: String? = null,
  val id: String? = null,
  val feedbackForm: DirectionDao? = null,
  val feedbackId: String? = null,
) {
  enum class DirectionDao {
    @JsonProperty("UP")
    UP,

    @JsonProperty("DOWN")
    DOWN
  }
}

data class RatingAckDao @Default constructor(
  val feedbackAck: Boolean ?= null,
  val ratingAck: Boolean ? = null,
)

