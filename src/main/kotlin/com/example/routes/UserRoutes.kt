package com.example.routes

import com.example.data.repository.UserRepository
import com.example.data.models.User
import com.example.requests.CreateAccountRequest
import com.example.requests.LoginRequest
import com.example.response.BasicApiResponse
import com.example.util.ApiResponseMessages.FIELDS_BLANK
import com.example.util.ApiResponseMessages.INVALID_CREDENTIALS
import com.example.util.ApiResponseMessages.USER_ALREADY_EXISTS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.createUserRoute(userRepository: UserRepository) {
    post("/api/user/create") {

            val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = userRepository.getUserByEmail(request.email) != null
            if(userExists) {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                       message =  USER_ALREADY_EXISTS
                    )
                )
                return@post
            }
            if(request.email.isBlank() || request.password.isBlank() || request.username.isBlank() ) {
                call.respond(
                    BasicApiResponse(
                        successful = false,
                       message = FIELDS_BLANK
                    )
                )
                return@post
            }
            userRepository.createUser(
                User(
                    email = request.email,
                    username = request.username,
                    password = request.password,
                    profileImageUrl = "",
                    bio = ""
                )
            )
            call.respond(
                BasicApiResponse(
                    successful = true)
            )
        }
    }


fun Route.loginUser(userRepository: UserRepository) {

    post("/api/user/login") {

            val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if(request.email.isBlank() || request.password.isBlank()) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isCorrerctPassword = userRepository.doesPasswordForUserMatch(
                email = request.email,
                enteredPassword = request.password
            )

            if(isCorrerctPassword) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = false,
                        message = INVALID_CREDENTIALS
                    )
                )
            }
    }
}