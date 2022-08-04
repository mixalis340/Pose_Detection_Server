package com.example.routes

import com.example.controller.UserController
import com.example.data.models.User
import com.example.requests.CreateAccountRequest
import com.example.response.BasicApiResponse
import com.example.util.ApiResponseMessages
import com.example.util.ApiResponseMessages.FIELDS_BLANK
import com.example.util.ApiResponseMessages.USER_ALREADY_EXISTS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.userRoutes() {
    val userController: UserController by inject()
    route("/api/user/create") {
        post {
            val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = userController.getUserByEmail(request.email) != null
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
            userController.createUser(
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
}