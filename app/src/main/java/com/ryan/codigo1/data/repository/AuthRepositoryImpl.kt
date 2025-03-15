package com.ryan.codigo1.data.repository

import com.ryan.codigo1.domain.model.User
import com.ryan.codigo1.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    // Mock implementation for register
    override suspend fun registerUser(user: User): Flow<Result<Boolean>> = flow {
        try {
            // Simulate network delay
            kotlinx.coroutines.delay(1000)
            // Simulate successful registration
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> = flow {
        emit(false) // Always logged out for this example
    }
}