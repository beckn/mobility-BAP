package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonProperty


data class ProtocolOperator @Default constructor(
  val name: String? = null,
  val image: String? = null,
  val dob: java.time.LocalDate? = null,
  )

