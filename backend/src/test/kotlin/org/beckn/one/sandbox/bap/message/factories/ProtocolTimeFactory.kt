package org.beckn.one.sandbox.bap.message.factories

import org.beckn.protocol.schemas.ProtocolSchedule
import org.beckn.protocol.schemas.ProtocolTime
import org.beckn.protocol.schemas.ProtocolTimeRange
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

object ProtocolTimeFactory {
  fun fixedTimestamp(label: String) = ProtocolTime(
    label = label,
    timestamp = OffsetDateTime.now(fixedClock),
    schedule = ProtocolSchedule("5",listOf(OffsetDateTime.now(fixedClock)), listOf(OffsetDateTime.now(fixedClock)))
  )

  fun fixedDuration(label: String) = ProtocolTime(
    label = label,
    duration = "5",
    schedule = ProtocolSchedule("5",listOf(OffsetDateTime.now(fixedClock)), listOf(OffsetDateTime.now(fixedClock)))
  )

  fun fixedDays(label: String) = ProtocolTime(
    label = label,
    days = "3",
    schedule = ProtocolSchedule("5", listOf(OffsetDateTime.now(fixedClock)), listOf(OffsetDateTime.now(fixedClock)))
  )

  fun fixedRange(label: String) = ProtocolTime(
    label = label,
    schedule = ProtocolSchedule("5",listOf(OffsetDateTime.now(fixedClock)), listOf(OffsetDateTime.now(fixedClock))),
    range = ProtocolTimeRange(
      start = OffsetDateTime.now(fixedClock),
      end = OffsetDateTime.now(fixedClock).plusDays(2)
    )
  )
}

val fixedClock: Clock = Clock.fixed(
  Instant.parse("2018-11-30T18:35:24.00Z"),
  ZoneId.of("UTC")
)