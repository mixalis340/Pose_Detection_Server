package com.example.plugins

import com.example.data.repository.UserRepository
import com.example.routes.createUserRoute
import com.example.routes.loginUser
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userRepository: UserRepository by inject()
        routing {
            createUserRoute(userRepository)
            loginUser(userRepository)
        }
}
