package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePhone @Inject constructor() {

    operator fun invoke(phone: String?): ValidationResult {
        // Phone is optional, so null or blank is valid
        if (phone.isNullOrBlank()) {
            return ValidationResult(isValid = true)
        }

        // Check if phone contains only valid characters
        if (!phone.all { it.isDigit() || it == '+' || it == '-' || it == ' ' }) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Phone number can only contain digits, '+', '-', and spaces"
            )
        }

        // Check if phone has at least 10 digits
        val digitCount = phone.count { it.isDigit() }
        if (digitCount < 10) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Phone number must contain at least 10 digits"
            )
        }

        return ValidationResult(isValid = true)
    }
}