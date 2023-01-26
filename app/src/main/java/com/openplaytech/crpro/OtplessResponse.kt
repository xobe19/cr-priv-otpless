package com.openplaytech.crpro

data class OtplessResponse(
    val `data`: Data,
    val ok: Boolean,
    val status: String,
    val statusCode: Int,
    val success: Boolean,
    val user: User
)