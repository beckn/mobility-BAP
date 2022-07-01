package org.beckn.one.sandbox.bap.message.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.beckn.protocol.schemas.Default

data class SubscriberDao @Default constructor(
  val subscriberId: String? = null,
  val type: SubscriberTypeDao? = null,
  val cbUrl: String? = null,
  val domain: String? = null,
  val city: String? = null,
  val country: String? = null,
  val signingPublicKey: String? = null,
  val encryptionPublicKey: String? = null,
  val status: SubscriberStatusDao? = null,
  val created: java.time.OffsetDateTime? = null,
  val updated: java.time.OffsetDateTime? = null,
  val expires: java.time.OffsetDateTime? = null,
) {
  enum class SubscriberStatusDao(val value: String) {
    @JsonProperty("INITIATED")
    INITIATED("INITIATED"),
    @JsonProperty("UNDER_SUBSCRIPTION")
    UNDERSUBSCRIPTION("UNDER_SUBSCRIPTION"),
    @JsonProperty("SUBSCRIBED")
    SUBSCRIBED("SUBSCRIBED"),
    @JsonProperty("INVALID_SSL")
    INVALIDSSL("INVALID_SSL"),
    @JsonProperty("UNSUBSCRIBED")
    UNSUBSCRIBED("UNSUBSCRIBED");
  }

  enum class SubscriberTypeDao(val value: String) {
    BAP("bap"),
    BPP("bpp"),
    BG("bg"),
    BPPR("bppr"),
    BGR("bgr");
  }
}
