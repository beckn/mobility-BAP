package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

import com.fasterxml.jackson.annotation.JsonProperty

data class FeedbackDao @Default constructor(
  val feedbackForm: FeedbackFormDao? = null,
  val feedbackUrl: FeedbackUrlDao? = null,
)

data class FeedbackFormDao(
  val id: String? = null,
  val question: String? = null,
  val parentId: String? = null,
  val answer: String? = null,
  val answerType: FeedbackAnswerTypeDao? = null,
) {
  enum class FeedbackAnswerTypeDao(val answerType: String) {
    RADIO("radio"),
    CHECKBOX("checkbox"),
    TEXT("text")
  }
}


data class FeedbackUrlDao(
  val url: String? = null,
  val tl_method: TlMethod? = null,
  val params: Map<String, String>? = null,
) {
  enum class TlMethod(val value: String) {
    @JsonProperty("http/get")
    GET("http/get"),
    @JsonProperty("http/post")
    POST("http/post");
  }

}

data class FeedbackFormElementDao @Default constructor(
  val id: String? = null,
  val question: String? = null,
  val parentId: String? = null,
  val answer: String? = null,
  val answerType: FeedbackAnswerTypeDao? = null,
) {
  enum class FeedbackAnswerTypeDao(val answerType: String) {
    RADIO("radio"),
    CHECKBOX("checkbox"),
    TEXT("text")
  }
}