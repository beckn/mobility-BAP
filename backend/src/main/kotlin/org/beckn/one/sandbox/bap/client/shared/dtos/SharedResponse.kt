package org.beckn.one.sandbox.bap.client.shared.dtos

import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolError
import org.beckn.protocol.schemas.ProtocolResponse

data class SharedResponse(override val context: ProtocolContext?, override val error: ProtocolError?) : ProtocolResponse