package com.example.response

data class BasicApiResponse(
    val successful: Boolean,
    val message: String? = null
)
