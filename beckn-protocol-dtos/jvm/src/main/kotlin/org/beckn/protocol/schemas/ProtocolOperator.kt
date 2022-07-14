package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty


data class ProtocolOperator @Default constructor(
  val name: String? = null,
  val image: String? = null,
  val dob: java.time.LocalDate? = null,
  val gender: String? = null,
  val cred: String? = null,
  val tags: Map<String, String>? = null,
  val experience: ProtocolExperience? = null,
  )

data class ProtocolExperience @Default constructor(
  val label: String? = null,
  val value: String? = null,
  val unit: String? = null
)