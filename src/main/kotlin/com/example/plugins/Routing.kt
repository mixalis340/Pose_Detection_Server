package com.example.plugins

import com.example.routes.createUserRoute
import com.example.routes.getUserProfile
import com.example.routes.loginUser
import com.example.routes.updateUserProfile
import com.example.service.UserService
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {


    val userService: UserService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    routing {
        createUserRoute(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
        )

        getUserProfile(userService)
        updateUserProfile(userService)

        static {
            resources("static")
        }
    }
}


