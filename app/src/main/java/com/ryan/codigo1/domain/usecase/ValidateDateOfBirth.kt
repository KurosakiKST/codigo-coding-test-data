package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ValidateDateOfBirth @Inject constructor() {

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    operator fun invoke(dateOfBirth: String?): ValidationResult {
        // Date of birth is optional, so null or blank is valid
        if (dateOfBirth.isNullOrBlank()) {
            return ValidationResult(isValid = true)
        }

        // Check date format
        return try {
            dateFormat.isLenient = false
            val parsedDate = dateFormat.parse(dateOfBirth)

            // Check if date is in the past
            val today = Calendar.getInstance().time
            if (parsedDate != null && parsedDate.after(today)) {
                ValidationResult(
                    isValid = false,
                    errorMessage = "Date of birth must be in the past"
                )
            } else {
                // Check if user is at least 13 years old (common minimum age for apps)
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.YEAR, -13)
                val minDate = calendar.time

                if (parsedDate != null && parsedDate.after(minDate)) {
                    ValidationResult(
                        isValid = false,
                        errorMessage = "You must be at least 13 years old"
                    )
                } else {
                    ValidationResult(isValid = true)
                }
            }
        } catch (e: Exception) {
            ValidationResult(
                isValid = false,
                errorMessage = "Please use format: MM/DD/YYYY"
            )
        }
    }
}