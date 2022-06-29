package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty

data class ProtocolSubscriber @Default constructor(
  val subscriberId: String? = null,
  val type: SubscriberType? = null,
  val cbUrl: String? = null,
  val domain: String? = null,
  val city: String? = null,
  val country: String? = null,
  val signingPublicKey: String? = null,
  val encryptionPublicKey: String? = null,
  val status: ProtocolSubscriberStatus? = null,
  val created: java.time.OffsetDateTime? = null,
  val updated: java.time.OffsetDateTime? = null,
  val expires: java.time.OffsetDateTime? = null,
) {
  enum class ProtocolSubscriberStatus(val value: String) {
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

  enum class SubscriberType(val value: String) {
    BAP("bap"),
    BPP("bpp"),
    BG("bg"),
    BPPR("bppr"),
    BGR("bgr");
  }
}

