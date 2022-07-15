package org.beckn.one.sandbox.bap.message.entities

import org.beckn.protocol.schemas.Default

data class OperatorDao @Default constructor(
    val name: String? = null,
    val image: String? = null,
    val dob: java.time.LocalDate? = null,
    val gender: String? = null,
    val cred: String? = null,
    val tags: Map<String, String>? = null,
    val experience: ExperienceDao? = null,
)
data class ExperienceDao @Default constructor(
    val label: String? = null,
    val value: String? = null,
    val unit: String? = null
)