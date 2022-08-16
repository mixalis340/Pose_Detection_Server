package com.example.data.repository.user

import com.example.data.models.User
import com.example.data.requests.UpdateProfileRequest

interface UserRepository {

    suspend fun createUser(user: User)

    suspend fun getUserById(id: String): User?

    suspend fun getUserByEmail(email : String): User?

    suspend fun doesPasswordForUserMatch(email: String, enteredPassword: String): Boolean

    suspend fun updateUser(
        userId: String,
        profileImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    )
    : Boolean
}