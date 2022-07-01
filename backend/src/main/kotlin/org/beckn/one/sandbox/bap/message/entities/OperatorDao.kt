package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class OperatorDao @Default constructor(
    val name: String? = null,
    val image: String? = null,
    val dob: java.time.LocalDate? = null,
)
