package org.beckn.one.sandbox.bap.message.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.beckn.protocol.schemas.*

data class CancellationDao @Default constructor(
    val type: CancellationTypeDao? = null,
    val refId: String? = null,
    val policies: PolicyDao? = null,
    val time: java.time.OffsetDateTime? = null,
    val cancelledBy: String? = null,
    val reasons: OptionDao? = null,
    val selectedReason: SelectedReasonDao? = null,
    val additionalDescription: DescriptorDao? = null,
) {
    enum class CancellationTypeDao(val value: String) {
        @JsonProperty(" full")
        FULL("full"),
        @JsonProperty("partial")
        PARTIAL("partial");
    }
}