package com.example.service

import com.example.data.models.User
import com.example.data.repository.user.UserRepository
import com.example.data.requests.CreateAccountRequest
import com.example.data.requests.LoginRequest
import com.example.data.requests.UpdateProfileRequest
import com.example.data.response.ProfileResponse
import com.example.util.Constants

class UserService (
    private  val userRepository: UserRepository
    ){

    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun getUserProfile(userId: String, callUserId: String) : ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return  null
        return  ProfileResponse(
                userId = user.id,
                username = user.username,
                bio = user.bio,
                profilePictureUrl = user.profileImageUrl,
                isOwnProfile = userId == callUserId
        )
    }

    suspend fun getUserByEmail(email : String) : User? {
        return userRepository.getUserByEmail(email)
    }

     fun isValidPassword(enteredPassword:String, actualPassword: String): Boolean {
         return enteredPassword == actualPassword
     }

    suspend fun updateUser(
        userId: String,
        profileImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        return userRepository.updateUser(
            userId,
            profileImageUrl,
            updateProfileRequest)
    }
    suspend fun doesPasswordMatchForUser(request: LoginRequest) : Boolean {
        return userRepository.doesPasswordForUserMatch(
            email = request.email,
            enteredPassword = request.password
        )
    }

    suspend fun createUser(request: CreateAccountRequest) {
      userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = Constants.DEFAULT_PROFILE_PICTURE_PATH,
                bio = ""
            )
      )
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): ValidationEvent{
        if(request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object Success : ValidationEvent()
    }
}