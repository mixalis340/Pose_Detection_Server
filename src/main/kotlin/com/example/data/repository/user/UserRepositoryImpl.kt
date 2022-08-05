package com.example.data.repository.user

import com.example.data.models.User
import com.example.data.repository.user.UserRepository
import com.example.data.requests.UpdateProfileRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImpl(
    val db: CoroutineDatabase
): UserRepository {

    private val users = db.getCollection<User>()

    override suspend fun createUser(user: User) {
        users.insertOne(user)
    }

    override suspend fun getUserById(id: String): User? {
        return users.findOneById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return  users.findOne(User::email eq email)
    }

    override suspend fun updateUser(
        userId: String,
        updateProfileRequest: UpdateProfileRequest,
        profileImageUrl: String
    ): Boolean {
        val user = getUserById(userId) ?: return false
        return users.updateOneById(
            id = userId,
            update = User(
                email = user.email,
                username = updateProfileRequest.username,
                password = user.password,
                profileImageUrl = profileImageUrl,
                bio = updateProfileRequest.bio,
                id = user.id
            )
        ).wasAcknowledged()
    }

    override suspend fun doesPasswordForUserMatch(
        email: String
        , enteredPassword: String
    ): Boolean {
        val user = getUserByEmail(email)
        return user?.password ==  enteredPassword
    }
}