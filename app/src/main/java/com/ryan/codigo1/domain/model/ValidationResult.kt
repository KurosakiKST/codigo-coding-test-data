package com.ryan.codigo1.domain.model

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)