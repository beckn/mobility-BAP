package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolFeedback @Default constructor(
  val feedbackForm: ProtocolFeedbackForm? = null,
  val feedbackUrl: ProtocolFeedbackUrl? = null,
)

data class ProtocolFeedbackForm(
  val id: String? = null,
  val question: String? = null,
  val parentId: String? = null,
  val answer: String? = null,
  val answerType: ProtocolFeedbackAnswerType? = null,
) {
  enum class ProtocolFeedbackAnswerType(val answerType: String) {
    RADIO("radio"),
    CHECKBOX("checkbox"),
    TEXT("text")
  }
}


data class ProtocolFeedbackUrl(
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

data class ProtocolFeedbackFormElement @Default constructor(
  val id: String? = null,
  val question: String? = null,
  val parentId: String? = null,
  val answer: String? = null,
  val answerType: ProtocolFeedbackAnswerType? = null,
) {
  enum class ProtocolFeedbackAnswerType(val answerType: String) {
    RADIO("radio"),
    CHECKBOX("checkbox"),
    TEXT("text")
  }
}