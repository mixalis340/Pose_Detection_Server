package com.example.routes



import com.example.plugins.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*


val ApplicationCall.userId: String
    get() = principal<JWTPrincipal>()?.userId.toString()