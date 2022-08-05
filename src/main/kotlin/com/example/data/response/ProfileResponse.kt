package com.example.data.response

data class ProfileResponse(
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isOwnProfile: Boolean
)
