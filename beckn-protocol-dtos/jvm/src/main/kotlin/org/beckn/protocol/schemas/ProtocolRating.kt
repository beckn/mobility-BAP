package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolRating @Default constructor(
  val value: Int? = null,
  val unit: String? = null,
  val max_value: Int? = null,
  val direction: ProtocolDirection? = null,
  val ratingCategory: String? = null,
  val id: String? = null,
  val feedbackForm: ProtocolFeedbackForm? = null,
  val feedbackId: String? = null,
){
  enum class ProtocolDirection {
    @JsonProperty("UP")
    UP,
    @JsonProperty("DOWN")
    DOWN
  }
}

data class ProtocolRatingAck @Default constructor(
  val feedbackAck: Boolean ?= null,
  val ratingAck: Boolean ? = null,
)