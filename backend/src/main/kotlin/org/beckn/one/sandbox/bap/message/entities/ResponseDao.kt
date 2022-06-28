package org.beckn.one.sandbox.bap.message.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.beckn.protocol.schemas.Default
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolError
import org.beckn.protocol.schemas.ProtocolResponse
import java.time.Clock
import java.time.LocalDateTime
import java.time.OffsetDateTime


interface BecknResponseDao {
  val context: ContextDao?
  val error: ErrorDao?
  val userId: String?
}

data class OnConfirmDao @Default constructor(
  override val context: ContextDao,
  val transactionId: String? = null,
  val messageId: String? = null,
  val message: OnConfirmMessageDao? = null,
  @field:JsonIgnore override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnConfirmMessageDao @Default constructor(
  val order: OrderDao? = null
)

data class AddDeliveryAddressDao @Default constructor(
  @field:JsonIgnore
  override val context: ContextDao? = null,
  override val userId: String?,
  @field:JsonIgnore
  override val error: ErrorDao? = null,
  val id: String,
  val descriptor: DescriptorDao? = null,
  val gps: String? = null,
  val defaultAddress: Boolean? = true,
  val address: AddressDao? = null
) : BecknResponseDao

data class BillingDetailsDao @Default constructor(
  @field:JsonIgnore
  override val context: ContextDao? = null,
  val id: String?,
  val name: String,
  val phone: String,
  val organization: OrganizationDao? = null,
  val address: AddressDao? = null,
  val email: String? = null,
  val time: TimeDao? = null,
  val taxNumber: String? = null,
  val createdAt: java.time.OffsetDateTime? = null,
  val updatedAt: java.time.OffsetDateTime? = null,
  val locationId: String? = null,
  @field:JsonIgnore
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class AccountDetailsDao @Default constructor(
  @field:JsonIgnore
  override val context: ContextDao? = null,
  val phone: String? = null,
  val email: String? = null,
  val name: String? = null,
  val address: List<DeliveryAddressDao>? = null,
  val billing: List<BillingDetailsDao>? = null,
  @field:JsonIgnore
  override val error: ErrorDao? = null,
  override var userId: String?,
  @JsonIgnore val clock: Clock = Clock.systemUTC(),
  val createdAt: OffsetDateTime = OffsetDateTime.now(clock),
) : BecknResponseDao

data class OnSearchDao @Default constructor(
  override val context: ContextDao,
  val message: OnSearchMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnSearchMessageDao @Default constructor(
  val catalog: CatalogDao? = null
)

data class OnSelectDao @Default constructor(
  override val context: ContextDao,
  val message: OnSelectMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnSelectMessageDao @Default constructor(
  val order: OnSelectMessageSelectedDao? = null
)

data class OnInitDao @Default constructor(
  override val context: ContextDao,
  val message: OnInitMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnInitMessageDao @Default constructor(
  val order: OnInitMessageInitializedDao? = null
)

data class OnTrackDao @Default constructor(
  override val context: ContextDao,
  val message: OnTrackMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnTrackMessageDao @Default constructor(
  val tracking: OnTrackMessageTrackingDao? = null
)

data class OnSupportDao @Default constructor(
  override val context: ContextDao,
  val message: OnSupportMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnSupportMessageDao @Default constructor(
  val phone: String? = null,
  val email: String? = null,
  val uri: String? = null
)

data class OnRatingDao @Default constructor(
  override val context: ContextDao,
  val message: OnRatingMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnRatingMessageDao @Default constructor(
  val feedback: OnRatingMessageFeedbackDao? = null
)

data class OnCancelDao @Default constructor(
  override val context: ContextDao,
  val message: OnCancelMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnCancellationReasonDao @Default constructor(
  override val context: ContextDao,
  val message: OnCancellationReasonsMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnCancelMessageDao @Default constructor(
  val order: OrderDao? = null
)

data class OnOrderStatusDao @Default constructor(
  override val context: ContextDao,
  val message: OnOrderStatusMessageDao? = null,
  override val error: ErrorDao? = null,
  override val userId: String?
) : BecknResponseDao

data class OnOrderStatusMessageDao @Default constructor(
  val order: OrderDao? = null
)

data class OnCancellationReasonsMessageDao @Default constructor(
  val cancellationReasons: List<OnCancellationReasonsDescripterDao>? = null,
  val ratingCategories: List<OnRatingCategoryDao>? = null
)

data class OnCancellationReasonsDescripterDao @Default constructor(
  val id: String? = null,
  val descriptor: DescriptorDao? = null
)

data class OnRatingCategoryDao @Default constructor(
  val id: String,
  val parentCategoryId: String? = null,
  val descriptor: DescriptorDao,
  val time: LocalDateTime? = null,
  val tags: Map<String, String>? = null,
  val question: String? = null
)