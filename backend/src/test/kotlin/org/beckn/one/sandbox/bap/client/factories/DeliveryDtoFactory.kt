package org.beckn.one.sandbox.bap.client.factories

import org.beckn.one.sandbox.bap.client.shared.dtos.DeliveryDto
import org.beckn.protocol.schemas.ProtocolAddress
import org.beckn.protocol.schemas.ProtocolLocation

class DeliveryDtoFactory {
  companion object {
    fun create() =
      DeliveryDto(
        name = "Test",
        phone = "9999999999",
        email = "test@gmail.com",
        type = "home-delivery",
        location = ProtocolLocation(
          address = ProtocolAddress(
            door = "A",
            country = "IND",
            city = "std:080",
            street = "Bannerghatta Road",
            areaCode = "560076",
            state = "KA",
            building = "Pine Apartments"
          ),
          gps = "12,77"
        )
      )
  }
}