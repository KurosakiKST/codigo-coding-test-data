package com.ryan.codigo1.domain.repository

import com.ryan.codigo1.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(user: User): Flow<Result<Boolean>>
    fun isUserLoggedIn(): Flow<Boolean>
}