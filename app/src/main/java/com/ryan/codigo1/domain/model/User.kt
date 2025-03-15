package com.ryan.codigo1.domain.model

data class User(
    val id: String = "",
    val name: String,
    val email: String,
    val password: String,
    val firstName: String = "",
    val lastName: String = "",
    val phone: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val nationality: String? = null,
    val countryOfResidence: String? = null,
    val countryCode: String? = null
)