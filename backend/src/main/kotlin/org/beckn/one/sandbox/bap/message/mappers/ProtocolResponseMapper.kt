package org.beckn.one.sandbox.bap.message.mappers

import org.beckn.one.sandbox.bap.message.entities.*
import org.beckn.protocol.schemas.*
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

interface ProtocolGenericResponseMapper<Protocol : ProtocolResponse, Entity : BecknResponseDao> {
  fun entityToProtocol(entity: Entity): Protocol
  fun protocolToEntity(schema: Protocol): Entity
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnSearchResponseMapper : ProtocolGenericResponseMapper<ProtocolOnSearch, OnSearchDao> {
  override fun entityToProtocol(entity: OnSearchDao): ProtocolOnSearch
  override fun protocolToEntity(schema: ProtocolOnSearch): OnSearchDao
}


@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnSelectResponseMapper : ProtocolGenericResponseMapper<ProtocolOnSelect, OnSelectDao> {
  override fun entityToProtocol(entity: OnSelectDao): ProtocolOnSelect
  override fun protocolToEntity(schema: ProtocolOnSelect): OnSelectDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnInitResponseMapper : ProtocolGenericResponseMapper<ProtocolOnInit, OnInitDao> {
  override fun entityToProtocol(entity: OnInitDao): ProtocolOnInit
  override fun protocolToEntity(schema: ProtocolOnInit): OnInitDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnProtocolConfirmResponseMapper : ProtocolGenericResponseMapper<ProtocolOnConfirm, OnConfirmDao> {
  override fun entityToProtocol(entity: OnConfirmDao): ProtocolOnConfirm
  override fun protocolToEntity(schema: ProtocolOnConfirm): OnConfirmDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnTrackResponseMapper : ProtocolGenericResponseMapper<ProtocolOnTrack, OnTrackDao> {
  override fun entityToProtocol(entity: OnTrackDao): ProtocolOnTrack
  override fun protocolToEntity(schema: ProtocolOnTrack): OnTrackDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnSupportResponseMapper : ProtocolGenericResponseMapper<ProtocolOnSupport, OnSupportDao> {
  override fun entityToProtocol(entity: OnSupportDao): ProtocolOnSupport
  override fun protocolToEntity(schema: ProtocolOnSupport): OnSupportDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnRatingResponseMapper : ProtocolGenericResponseMapper<ProtocolOnRating, OnRatingDao> {
  override fun entityToProtocol(entity: OnRatingDao): ProtocolOnRating
  override fun protocolToEntity(schema: ProtocolOnRating): OnRatingDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnCancelResponseMapper : ProtocolGenericResponseMapper<ProtocolOnCancel, OnCancelDao> {
  override fun entityToProtocol(entity: OnCancelDao): ProtocolOnCancel
  override fun protocolToEntity(schema: ProtocolOnCancel): OnCancelDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnOrderStatusResponseMapper : ProtocolGenericResponseMapper<ProtocolOnOrderStatus, OnOrderStatusDao> {
  override fun entityToProtocol(entity: OnOrderStatusDao): ProtocolOnOrderStatus
  override fun protocolToEntity(schema: ProtocolOnOrderStatus): OnOrderStatusDao
}

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.WARN,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  uses = [DateMapper::class]
)
interface OnCancellationReasonResponseMapper : ProtocolGenericResponseMapper<ProtocolOnCancellationReasons, OnCancellationReasonDao> {
  override fun entityToProtocol(entity: OnCancellationReasonDao): ProtocolOnCancellationReasons
  override fun protocolToEntity(schema: ProtocolOnCancellationReasons): OnCancellationReasonDao
}
