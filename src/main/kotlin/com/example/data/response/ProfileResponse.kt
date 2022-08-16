package com.example.data.response

data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    val profilePictureUrl: String,
    val isOwnProfile: Boolean
)
