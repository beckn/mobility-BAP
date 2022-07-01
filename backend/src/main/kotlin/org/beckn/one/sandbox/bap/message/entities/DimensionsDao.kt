package org.beckn.one.sandbox.bap.message.entities

data class DimensionsDao(
    val length: ScalarDao? = null,
    val breadth: ScalarDao? = null,
    val height: ScalarDao? = null,
)
