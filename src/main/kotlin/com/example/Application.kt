package com.example

import com.example.di.mainModule
import io.ktor.server.application.*
import com.example.plugins.*
import org.koin.ktor.plugin.Koin
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.nio.file.Paths

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    install(Koin) {
        modules(mainModule)
    }

    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()

}
